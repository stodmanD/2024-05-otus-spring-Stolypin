package com.example.employeemanagement.controller.crudl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.employeemanagement.dto.PersonalInfoUpdateDto;
import com.example.employeemanagement.dto.PersonalInfoDto;
import com.example.employeemanagement.service.PersonalInfoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonalInfoCrudlController {
    
    private final PersonalInfoService personalInfoService;
    

    @PostMapping("/personal_info")
    public PersonalInfoDto create(@RequestBody PersonalInfoUpdateDto personalInfoUpdateDto) {
        return personalInfoService.create(
                personalInfoUpdateDto.fullName(),
                personalInfoUpdateDto.birthdate(),
                personalInfoUpdateDto.employmentDate(),
                personalInfoUpdateDto.isMan()
        );
    }

    @GetMapping("/personal_info/{id}")
    public PersonalInfoDto read(@PathVariable long id) {
        return personalInfoService.findById(id);
    }

    @PutMapping("/personal_info/{id}")
    public PersonalInfoDto update(@PathVariable long id, @RequestBody PersonalInfoUpdateDto personalInfoUpdateDto) {
        return personalInfoService.update(
                id,
                personalInfoUpdateDto.fullName(),
                personalInfoUpdateDto.birthdate(),
                personalInfoUpdateDto.employmentDate(),
                personalInfoUpdateDto.isMan()
        );
    }

    @DeleteMapping("/personal_info/{id}")
    public void delete(@PathVariable long id) {
        personalInfoService.delete(id);
    }

    @GetMapping("/personal_info")
    public List<PersonalInfoDto> list() {
        return personalInfoService.findAll();
    }
    
}
