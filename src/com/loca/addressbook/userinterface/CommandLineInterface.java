package com.loca.addressbook.userinterface;

import com.loca.addressbook.Application;
import com.loca.addressbook.registry.LocalRegistry;
import com.loca.addressbook.remoteregistry.RemoteRegistry;
import com.loca.addressbook.userinterface.commands.Command;
import com.loca.addressbook.exceptions.InvalidCommandException;
import com.loca.addressbook.exceptions.InvalidCommandParameterException;

public class CommandLineInterface implements InputHandler {

    private static final String WELCOME_MESSAGE = "Welcome to AddressBook 2.0!";
	private Console console;
	private CommandInterpreter commandInterpreter;

	public CommandLineInterface(LocalRegistry localRegistry, RemoteRegistry remoteRegistry, Console console, Application application) {
	    this.console = console;
	    this.commandInterpreter = new CommandInterpreter(console, localRegistry, remoteRegistry, application);
	    console.registerInputHandler(this);

    }

    public void start() {
        console.print(WELCOME_MESSAGE);
	    console.readUserInput();
    }

    @Override
    public void handle(CommandLine commandLine) {
        try {
            Command command = commandInterpreter.interpret(commandLine);
            command.execute();
        } catch (InvalidCommandException | InvalidCommandParameterException e) {
            console.print(e.getMessage());
        }

    }
    
}
