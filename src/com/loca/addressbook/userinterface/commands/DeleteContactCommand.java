package com.loca.addressbook.userinterface.commands;

import com.loca.addressbook.registry.LocalRegistry;
import com.loca.addressbook.userinterface.ConsolePrinter;
import com.loca.addressbook.exceptions.InvalidCommandParameterException;
import com.loca.addressbook.exceptions.InvalidContactId;

import java.util.List;

public class DeleteContactCommand implements Command {

    private CommandType commandType = CommandType.DELETE;
    private ConsolePrinter consolePrinter;
    private LocalRegistry localRegistry;
    private List<String> parameters;

    public DeleteContactCommand (ConsolePrinter consolePrinter, LocalRegistry localRegistry, List<String> parameters) {
    	this.consolePrinter = consolePrinter;
        this.localRegistry = localRegistry;
        this.parameters = parameters;
    }

    @Override
    public void execute() throws InvalidCommandParameterException {
        validate();
        deleteContactFromRegistry();
    }

    private void deleteContactFromRegistry() {
        String uuid = parameters.get(0);
        String message;
        try {
        	localRegistry.deleteContact(uuid);
        	message = commandType.getSuccessMessage();
        } catch (InvalidContactId e) {
        	message = commandType.getFailureMessage();
        }
        consolePrinter.print(message);
    }

    private void validate() throws InvalidCommandParameterException {
        if (parameters.size() != commandType.getParametersCount()) {
            throw new InvalidCommandParameterException(commandType, parameters);
        }
    }
}
