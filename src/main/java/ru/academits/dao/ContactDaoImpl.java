package ru.academits.dao;

import org.springframework.stereotype.Repository;
import ru.academits.model.Contact;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ContactDaoImpl extends GenericDaoImpl<Contact, Long> implements ContactDao {
    public ContactDaoImpl() {
        super(Contact.class);
    }

    @Override
    public List<Contact> getAllContacts(String term) {
        return findAll(term);
    }

    @Override
    public List<Contact> findByPhone(String phone) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> cq = cb.createQuery(clazz);

        Root<Contact> root = cq.from(clazz);

        Predicate unDeletedPredicate = cb.equal(root.get("isDeleted"), false);
        Predicate samePhonePredicate = cb.equal(root.get("phone"), phone);

        Predicate finalPredicate = cb.and(unDeletedPredicate, samePhonePredicate);

        cq.where(finalPredicate);

        CriteriaQuery<Contact> select = cq.select(root);
        TypedQuery<Contact> q = entityManager.createQuery(select);

        return q.getResultList();
    }

    @Override
    public void deleteContact(Contact contactToDelete) {
        contactToDelete.setDeleted(true);
        update(contactToDelete);
    }
}
