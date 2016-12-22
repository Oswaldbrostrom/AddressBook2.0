package com.loca.addressbook.registry;

import java.io.*;
import java.util.List;

public class RegistryPersister {

    private Registry registry;

    public RegistryPersister(Registry register) {
        this.registry = register;
    }

    public void load() {

        File f = new File("contacts.data");

        if (f.isFile() && f.canRead()) {
            try {
                FileInputStream fileIn = new FileInputStream("contacts.data");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                registry.load((List<Contact>) in.readObject());
                in.close();
                fileIn.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("contacts.data");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(registry.getContacts());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

