package com.example.hw04springshell.shell;

import com.example.hw04springshell.service.TestRunnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent(value = "Test App Commands")
@RequiredArgsConstructor
public class ApplicationCommands {
    private final TestRunnerService testRunnerService;

    @ShellMethod(value = "Start Test Command", key = {"s", "start"})
    public void startTest() {
        testRunnerService.run();
    }
}
