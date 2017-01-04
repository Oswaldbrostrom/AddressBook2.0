package com.loca.addressbook.userinterface;

import com.loca.addressbook.Application;
import com.loca.addressbook.registry.LocalRegistry;
import com.loca.addressbook.remoteregistry.RemoteRegistry;
import com.loca.addressbook.userinterface.commands.*;
import com.loca.addressbook.exceptions.InvalidCommandException;

import java.util.List;

public class CommandInterpreter {

    private LocalRegistry localRegistry;
    private RemoteRegistry remoteRegistry;
    private ConsolePrinter consolePrinter;
    private Application application;

    public CommandInterpreter(ConsolePrinter consolePrinter, LocalRegistry localRegistry, RemoteRegistry remoteRegistry, Application application) {
        this.localRegistry = localRegistry;
        this.remoteRegistry = remoteRegistry;
        this.consolePrinter = consolePrinter;
        this.application = application;
    }

    public Command interpret(CommandLine commandLine) throws InvalidCommandException {
        CommandType commandType = findCommandType(commandLine.getCommand());
        Command command = createCommand(commandType, commandLine.getParameters());
        return command;
	}

    private CommandType findCommandType(String command) throws InvalidCommandException {
	    CommandType inputCommandType = null;
	    for (CommandType commandType: CommandType.values()) {
	        if (commandType.getCommandName().equals(command)) {
	            inputCommandType = commandType;
	            break;
            }
        }
        if (inputCommandType == null) {
            throw new InvalidCommandException(command);
        }
        return inputCommandType;
    }

    private Command createCommand(CommandType commandType, List<String> parameters) {
	    Command command = null;
	    switch (commandType) {
            case ADD:
                command = new AddContactCommand(consolePrinter, localRegistry, parameters);
                break;
            case LIST:
                command = new ListCommand(consolePrinter, localRegistry, remoteRegistry, parameters);
                break;
            case DELETE:
                command = new DeleteContactCommand(consolePrinter, localRegistry, parameters);
                break;
            case HELP:
                command = new HelpCommand(consolePrinter, parameters);
                break;
            case SEARCH:
                command = new SearchCommand(consolePrinter, localRegistry, remoteRegistry, parameters);
                break;
            case QUIT:
                command = new QuitCommand(consolePrinter, parameters, application);
                break;
        }

        return command;
    }


}
