package ru.academits.dao;

import ru.academits.model.Contact;

import java.util.List;

public interface ContactDao extends GenericDao<Contact, Long> {
    List<Contact> getAllContacts(String term);

    List<Contact> findByPhone(String phone);

    void deleteContact(Contact contact);
}
