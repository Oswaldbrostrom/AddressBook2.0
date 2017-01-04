package com.loca.addressbook.remoteregistry;

import java.util.ArrayList;
import java.util.List;

public class RemoteRegistry {

    private List<RemoteContact> remoteRegistry = new ArrayList<>();

    public List<RemoteContact> getContacts() {
        return remoteRegistry;
    }

    public List<RemoteContact> search(String search) {          // dubbel kod, strider mot solid.
        List<RemoteContact> tempRegistry = new ArrayList <>();
        for (RemoteContact contact : remoteRegistry){
            if(contact.getFirstName().toLowerCase().startsWith(search.toLowerCase()) ||
                    contact.getLastName().toLowerCase().startsWith(search.toLowerCase())){
                tempRegistry.add(contact);
            }
        }
        return tempRegistry;
    }

    public void add(String id, String firstName, String lastName, String email) {

        remoteRegistry.add(new RemoteContact(id, firstName, lastName, email));
    }
}
