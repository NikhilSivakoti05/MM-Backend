package com.sgcore.backend.service;

import com.sgcore.backend.model.Contact;
import com.sgcore.backend.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

   
    public Optional<Contact> getContactById(String id) {
        return contactRepository.findById(id);
    }

   
    public void deleteContact(String id) {
        contactRepository.deleteById(id);
    }
}

