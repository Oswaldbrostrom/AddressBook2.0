package com.loca.addressbook.remoteregistry;

import com.loca.addressbook.registry.Contact;
import com.loca.addressbook.registry.ContactDetails;

public class RemoteContact implements Contact {
    private String uuid;
    private ContactDetails contactDetails;


    public RemoteContact(String uuid, String firstName, String lastName, String email ) {
        contactDetails = new ContactDetails(firstName,lastName,email);
        this.uuid = uuid;
    }

    @Override
    public String getId() {
        return this.uuid;
    }

    @Override
    public String getFirstName() {
        return contactDetails.getFirstName();
    }

    @Override
    public String getLastName() {
        return contactDetails.getLastName();
    }

    @Override
    public String getEmailAddress() {
        return contactDetails.getEmail();
    }
}
