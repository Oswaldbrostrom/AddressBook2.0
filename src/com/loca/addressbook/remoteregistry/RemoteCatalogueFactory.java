package com.loca.addressbook.remoteregistry;

public class RemoteCatalogueFactory {

    private int defaultPort;

    public RemoteCatalogueFactory(int defaultPort) {
        this.defaultPort = defaultPort;
    }       //åtgärda

    public RemoteCatalogueProxy create(String host) {
        return new AtomicRemoteCatalogueProxy(host, defaultPort);
    }
}
