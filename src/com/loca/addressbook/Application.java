package com.loca.addressbook;

import java.util.ArrayList;
import java.util.List;

import com.loca.addressbook.registry.AutoSave;
import com.loca.addressbook.registry.LocalRegistry;
import com.loca.addressbook.registry.RegistryPersister;
import com.loca.addressbook.remoteregistry.CatalogueLoader;
import com.loca.addressbook.remoteregistry.RemoteRegistry;
import com.loca.addressbook.userinterface.CommandLineInterface;
import com.loca.addressbook.userinterface.Console;

public class Application {

    public static void main(String[] args) {
        Application application = new Application();
        application.start();
    }
	
	private static final String HOSTNAME_1 = "172.20.200.157";		//<- mer än 1 ansvar.
	private static final String HOSTNAME_2 = "172.20.200.173";
	private static final String HOSTNAME_3 = "172.20.201.62";
    private Console console = new Console();
    private LocalRegistry localRegistry = new LocalRegistry();
    private RegistryPersister registryPersister = new RegistryPersister(localRegistry);
    private RemoteRegistry remoteRegistry = new RemoteRegistry();

    public void start() {
    	initiateLocalContacts();
		initiateServerContacts();
		startAutoSaveDaemon();
		initiateCommandLineInterface();
    }

	private void initiateLocalContacts() {
        registryPersister.load();
	}

	private void initiateServerContacts() {
    	List<String> hostNames = makeHostNames();
		for(String hostName : hostNames) {
			Runnable runnable = new CatalogueLoader(remoteRegistry, hostName);
			Thread catalogueLoader = new Thread(runnable);
	        catalogueLoader.setDaemon(true);
	        catalogueLoader.start();
		}
	}
	
	private List<String> makeHostNames() {
		List<String> hostNames = new ArrayList<>();
		hostNames.add(HOSTNAME_1);
		hostNames.add(HOSTNAME_2);
		hostNames.add(HOSTNAME_3);
		return hostNames;
	}

	private void startAutoSaveDaemon() {		//Ska göras i AutoSave klassen.
		Runnable runnable  = new AutoSave(registryPersister);
    	Thread autoSave = new Thread(runnable);
        autoSave.setDaemon(true);
        autoSave.start();
	}
	
	private void initiateCommandLineInterface() {
		CommandLineInterface commandLineInterface = new CommandLineInterface(localRegistry, remoteRegistry, console, this);
		commandLineInterface.start();
	}
	 
	public void quit() {
		registryPersister.save();
		console.close();
	}
}