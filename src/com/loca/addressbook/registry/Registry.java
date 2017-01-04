package com.loca.addressbook.registry;

import com.loca.addressbook.exceptions.InvalidContactId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Registry {

    private List<Contact> localContactList = new ArrayList<>();


    public void addContact(String firstName, String lastName, String email) {

        localContactList.add(new LocalContact(firstName, lastName, email, UUID.randomUUID()));      // <- UML
    }

    public List<Contact> getContacts() {
        return this.localContactList;
    }

    public void deleteContact(String uuid) throws InvalidContactId {

        int i = 0;
        for (Contact contact : localContactList) {
            if (contact.getId().equals(uuid)) {
                localContactList.remove(i);
                return;
            }
            i++;
        }
        throw new InvalidContactId();
    }

    public List<Contact> search(String search) {
        List<Contact> tempRegistry = new ArrayList<>();
        for (Contact contact : localContactList) {
            if (contact.getFirstName().toLowerCase().startsWith(search.toLowerCase()) ||
                    contact.getLastName().toLowerCase().startsWith(search.toLowerCase())) {
                tempRegistry.add(contact);
            }
        }
        return tempRegistry;

    }

    public void load(List<Contact> localRegistry) {
        this.localContactList = localRegistry;
    }
}
