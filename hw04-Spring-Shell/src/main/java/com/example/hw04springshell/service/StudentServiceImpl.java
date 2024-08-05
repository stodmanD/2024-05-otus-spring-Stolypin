package com.example.hw04springshell.service;

import com.example.hw04springshell.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final LocalizedIOService localizedIoService;

    @Override
    public Student determineCurrentStudent() {
        var firstName = localizedIoService.readStringWithPromptLocalized("StudentService.input.first.name");
        var lastName = localizedIoService.readStringWithPromptLocalized("StudentService.input.last.name");
        return new Student(firstName, lastName);
    }
}
