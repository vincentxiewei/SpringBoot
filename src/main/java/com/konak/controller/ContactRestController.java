package com.konak.controller;
import java.util.ArrayList;
import java.util.List;

import com.konak.model.Contact;
import com.konak.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
public class ContactRestController {

    @Autowired
    private ContactRepository repo;

    @RequestMapping(method=RequestMethod.GET)
    public List<Contact> getAll() {
        System.out.println("got in GET");
        List<Contact> target = new ArrayList<Contact>();
        repo.findAll().forEach(target::add);
        return target;
    }

    @RequestMapping(method=RequestMethod.POST)
    public Contact create(@RequestBody Contact contact) {
        System.out.println("got in POST");
        System.out.println(contact.getLastName());
        return repo.save(contact);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="{id}")
    public void delete(@PathVariable String id) {
        System.out.println("got in DELETE");
        repo.delete(id);
    }

    @RequestMapping(method=RequestMethod.PUT, value="{id}")
    public Contact update(@PathVariable String id, @RequestBody Contact contact) {
        System.out.println("got in PUT");
        Contact update = repo.findOne(id);
        update.setAddress(contact.getAddress());
        update.setEmail(contact.getEmail());
        update.setFacebookProfile(contact.getFacebookProfile());
        update.setFirstName(contact.getFirstName());
        update.setGooglePlusProfile(contact.getGooglePlusProfile());
        update.setLastName(contact.getLastName());
        update.setLinkedInProfile(contact.getLinkedInProfile());
        update.setPhoneNumber(contact.getPhoneNumber());
        update.setTwitterHandle(contact.getTwitterHandle());
        return repo.save(update);
    }

}