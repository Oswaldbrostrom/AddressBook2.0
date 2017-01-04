package com.loca.addressbook.remoteregistry;

public class CatalogueLoader implements Runnable{

    private RemoteCatalogueFactory catalogueFactory = new RemoteCatalogueFactory();
    private RemoteRegistry remoteRegistry;
    private String hostName;

    public CatalogueLoader(RemoteRegistry remoteRegistry, String hostName) {
        this.remoteRegistry = remoteRegistry;
        this.hostName = hostName;
    }

    @Override
    public void run() {
        RemoteCatalogueProxy remoteCatalogueProxy = catalogueFactory.create(hostName);

        for(String contact : remoteCatalogueProxy.getContacts()) {
            String[] splittedList = contact.split(" ");
            remoteRegistry.add(splittedList[0],splittedList[1],splittedList[2],splittedList[3]);
        }

    }
}
