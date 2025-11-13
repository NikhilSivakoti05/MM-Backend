package com.sgcore.backend.controller;

import com.sgcore.backend.model.Contact;
import com.sgcore.backend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")

public class ContactController {

    @Autowired
    private ContactService contactService;


    @PostMapping
    public ResponseEntity<?> createContact(@RequestBody Contact contact) {

        // Manual validation instead of Jakarta
        if (contact.getFullName() == null || contact.getFullName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Full name is required");
        }
        if (contact.getEmail() == null || contact.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        Contact saved = contactService.saveContact(contact);
        return ResponseEntity.ok(saved);
    }


    @GetMapping
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@PathVariable String id) {
        return contactService.getContactById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable String id, @RequestBody Contact updated) {
        return contactService.getContactById(id)
                .map(existing -> {
                    existing.setFullName(updated.getFullName());
                    existing.setEmail(updated.getEmail());
                    existing.setCompany(updated.getCompany());
                    existing.setMessage(updated.getMessage());
                    return ResponseEntity.ok(contactService.saveContact(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable String id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
