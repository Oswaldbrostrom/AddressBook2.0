package com.loca.addressbook.userinterface.commands;

import java.util.ArrayList;
import java.util.List;

import com.loca.addressbook.exceptions.InvalidCommandParameterException;
import com.loca.addressbook.registry.Contact;
import com.loca.addressbook.registry.Registry;
import com.loca.addressbook.remoteregistry.RemoteRegistry;
import com.loca.addressbook.userinterface.ConsolePrinter;
import com.loca.addressbook.userinterface.ContactFormatter;
import com.loca.addressbook.userinterface.ContactListSorter;

public class SearchCommand implements Command {
	
	private CommandType commandType = CommandType.SEARCH;
	private ConsolePrinter consolePrinter;
	private Registry registry;
	private RemoteRegistry remoteRegistry;
	private List<String> parameters;
	
	public SearchCommand (ConsolePrinter consolePrinter, Registry registry, RemoteRegistry remoteRegistry, List<String> parameters) {
		this.consolePrinter = consolePrinter;
		this.registry = registry;
		this.remoteRegistry = remoteRegistry;
		this.parameters = parameters;
	}

    @Override
    public void execute() throws InvalidCommandParameterException {
    	validate();
    	searchContacts();
    }
    
    private void searchContacts() {				//<-- liknar listContacts i klassen ListeCommand. WET princip
    	String output;
    	String searchWord = parameters.get(0);
		List<Contact> contacts = new ArrayList<>();
		contacts.addAll(registry.search(searchWord));
		contacts.addAll(remoteRegistry.search(searchWord));

		if (contacts.isEmpty()) {
			output = commandType.getFailureMessage();
		} else {
			List<Contact> sortedContacts = ContactListSorter.sort(contacts);
			output = makeOutput(sortedContacts);
		}
		consolePrinter.print(output);
	}

	private String makeOutput(List<Contact> contacts) {		//dubbel kod för ListCommand
		StringBuilder output = new StringBuilder(1000);	//onödig optimering, finns även i ListCommand.
		for(Contact contact : contacts) {
			String formattedContact = ContactFormatter.format(contact);
			output.append(formattedContact);
		}
		return output.toString();
	}

	private void validate() throws InvalidCommandParameterException {
		if (parameters.size() != commandType.getParametersCount()) {
			throw new InvalidCommandParameterException(commandType, parameters);
		}
	}
}
