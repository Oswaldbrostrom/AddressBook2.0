package com.loca.addressbook.registry;

import com.loca.addressbook.userinterface.Console;

import java.io.*;
import java.util.List;

public class RegistryPersister {

    private LocalRegistry localRegistry;
    private Console console;

    public RegistryPersister(LocalRegistry register) {
        this.localRegistry = register;
    }

    public void load() {

        File file = new File("contacts.data");

        if (file.isFile() && file.canRead()) {
            try {
                FileInputStream fileIn = new FileInputStream("contacts.data");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                localRegistry.load((List<Contact>) in.readObject());
                in.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException e) {
                console.print(e.getMessage());
            }
        }
    }

    public synchronized void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("contacts.data");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(localRegistry.getContacts());
        } catch (IOException e) {
            console.print(e.getMessage());
        }
    }
}

