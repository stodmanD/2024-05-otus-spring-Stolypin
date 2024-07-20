package com.example.hw03springboot.service.impl;

import com.example.hw03springboot.domain.Student;
import com.example.hw03springboot.service.LocalizedIOService;
import com.example.hw03springboot.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final LocalizedIOService ioService;

    @Override
    public Student determineCurrentStudent() {
        var firstName = ioService.readStringWithPromptLocalized("user.firstName");
        var lastName = ioService.readStringWithPromptLocalized("user.lastName");
        return new Student(firstName, lastName);
    }

}
