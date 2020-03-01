package ru.academits.dao;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Transactional
public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {
    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> clazz;

    public GenericDaoImpl(Class<T> type) {
        this.clazz = type;
    }

    @Transactional
    @Override
    public void create(T obj) {
        entityManager.persist(obj);
    }

    @Transactional
    @Override
    public void update(T obj) {
        entityManager.merge(obj);
    }

    @Transactional
    @Override
    public void remove(T obj) {
        entityManager.remove(obj);
    }

    @Override
    public T getById(PK id) {
        return entityManager.find(clazz, id);
    }

    @Transactional
    @Override
    public List<T> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);

        Root<T> root = cq.from(clazz);

        Predicate unDeletedPredicate = cb.equal(root.get("isDeleted"), false);

        Predicate firstNameFilterPredicate = cb.like(root.get("firstName"), "%w%");
        Predicate lastNameFilterPredicate = cb.like(root.get("lastName"), "%w%");
        Predicate nameFilterPredicate = cb.or(firstNameFilterPredicate, lastNameFilterPredicate);

        cq.where(cb.and(
                unDeletedPredicate,
                nameFilterPredicate
        ));

        CriteriaQuery<T> select = cq.select(root);
        TypedQuery<T> q = entityManager.createQuery(select);

        return q.getResultList();
    }
}
