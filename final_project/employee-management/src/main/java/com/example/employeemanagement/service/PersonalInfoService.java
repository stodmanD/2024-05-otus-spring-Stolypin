package com.example.employeemanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.employeemanagement.dto.PersonalInfoDto;
import com.example.employeemanagement.exception.EntityNotFoundException;
import com.example.employeemanagement.model.PersonalInfo;
import com.example.employeemanagement.repository.PersonalInfoRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalInfoService {

    private final PersonalInfoRepository personalInfoRepository;


    @Transactional
    public PersonalInfoDto create(String fullName, LocalDate birthdate, LocalDate employmentDate, boolean isMan) {
        PersonalInfo personalInfo = new PersonalInfo(fullName, birthdate, employmentDate, isMan);
        PersonalInfo savedPersonalInfo = personalInfoRepository.save(personalInfo);
        return PersonalInfoDto.fromEntity(savedPersonalInfo);
    }

    @Transactional(readOnly = true)
    public PersonalInfoDto findById(long id) {
        return personalInfoRepository.findById(id)
                .map(PersonalInfoDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("PersonalInfo with id=%d not found".formatted(id)));
    }

    @Transactional
    public PersonalInfoDto update(long id,
                                  String fullName,
                                  LocalDate birthdate,
                                  LocalDate employmentDate,
                                  boolean isMan) {
        PersonalInfo personalInfo = personalInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PersonalInfo with id=%d not found".formatted(id)));

        personalInfo.setFullName(fullName);
        personalInfo.setBirthdate(birthdate);
        personalInfo.setEmploymentDate(employmentDate);
        personalInfo.setMan(isMan);

        PersonalInfo updatedPersonalInfo = personalInfoRepository.save(personalInfo);
        return PersonalInfoDto.fromEntity(updatedPersonalInfo);
    }

    @Transactional
    public void delete(long id) {
        personalInfoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PersonalInfoDto> findAll() {
        return personalInfoRepository.findAll()
                .stream()
                .map(PersonalInfoDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PersonalInfoDto> birthdayToday() {
        return personalInfoRepository.findBirthdayToday()
                .stream()
                .map(PersonalInfoDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PersonalInfoDto> birthdayNextMonth() {
        return personalInfoRepository.findBirthdayNextMonth()
                .stream()
                .map(PersonalInfoDto::fromEntity)
                .toList();
    }

}
