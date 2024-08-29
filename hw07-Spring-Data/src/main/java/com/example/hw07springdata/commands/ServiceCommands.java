package com.example.hw07springdata.commands;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.sql.SQLException;

@ShellComponent
public class ServiceCommands {
    @ShellMethod(value = "Start H2 console", key = "console")
    public void startConsole() throws SQLException {
        Console.main();
    }
}
