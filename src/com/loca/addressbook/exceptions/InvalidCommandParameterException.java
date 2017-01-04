package com.loca.addressbook.exceptions;

import com.loca.addressbook.userinterface.commands.CommandType;

import java.util.List;

public class InvalidCommandParameterException extends Exception {
    private static final String QUOTATION_MARK = "\""; //??!!

    public InvalidCommandParameterException (CommandType commandType, List<String> parameters) {
        super("Command Line failed! Received " + parameters.size() + " parameters while expecting " + commandType.getParametersCount() + " parameters.");
    }
}