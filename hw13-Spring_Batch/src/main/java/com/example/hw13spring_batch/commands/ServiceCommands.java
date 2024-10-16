package com.example.hw13spring_batch.commands;

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
