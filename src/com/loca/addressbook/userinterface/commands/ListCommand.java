package com.loca.addressbook.userinterface.commands;

import java.util.ArrayList;
import java.util.List;

import com.loca.addressbook.exceptions.InvalidCommandParameterException;
import com.loca.addressbook.registry.Contact;
import com.loca.addressbook.registry.LocalRegistry;
import com.loca.addressbook.remoteregistry.RemoteRegistry;
import com.loca.addressbook.userinterface.ConsolePrinter;
import com.loca.addressbook.userinterface.ContactFormatter;
import com.loca.addressbook.userinterface.ContactListSorter;

public class ListCommand implements Command {
	
	private CommandType commandType = CommandType.LIST;
	private ConsolePrinter consolePrinter;
	private LocalRegistry localRegistry;
	private RemoteRegistry remoteRegistry;
	private List<String> parameters;
	
	public ListCommand (ConsolePrinter consolePrinter, LocalRegistry localRegistry, RemoteRegistry remoteRegistry, List<String> parameters) {
		this.consolePrinter = consolePrinter;
		this.localRegistry = localRegistry;
		this.remoteRegistry = remoteRegistry;
		this.parameters = parameters;
	}

    @Override
    public void execute() throws InvalidCommandParameterException {
    	validate();
    	listContacts();
    }

	private void listContacts() {
		String output;
		List<Contact> contacts = new ArrayList<>();
		contacts.addAll(localRegistry.getContacts());
		contacts.addAll(remoteRegistry.getContacts());
		if (contacts.isEmpty()) {
			output = commandType.getFailureMessage();
		} else {
			List<Contact> sortedContacts = ContactListSorter.sort(contacts);
			output = makeOutput(sortedContacts);
		}
		consolePrinter.print(output);
	}

	private String makeOutput(List<Contact> contacts) {
		StringBuilder output = new StringBuilder(1000);
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