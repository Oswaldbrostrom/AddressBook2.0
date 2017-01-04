package com.loca.addressbook.remoteregistry;

public class RemoteCatalogueFactory {

    private int defaultPort = 61616;
    //ll

    public RemoteCatalogueFactory() {

    }

    public RemoteCatalogueProxy create(String host) {
        return new AtomicRemoteCatalogueProxy(host, defaultPort);
    }
}
