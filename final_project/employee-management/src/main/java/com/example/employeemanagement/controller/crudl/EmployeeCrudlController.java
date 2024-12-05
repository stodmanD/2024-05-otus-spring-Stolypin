package com.example.employeemanagement.controller.crudl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.employeemanagement.dto.EmployeeUpdatedDto;
import com.example.employeemanagement.dto.EmployeeDto;
import com.example.employeemanagement.service.EmployeeService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EmployeeCrudlController {

    private final EmployeeService employeeService;


    @PostMapping("/employee")
    public EmployeeDto create(@RequestBody EmployeeUpdatedDto employeeUpdatedDto) {

        return employeeService.create(employeeUpdatedDto);
    }

    @GetMapping("/employee/{id}")
    public EmployeeDto read(@PathVariable long id) {
        return employeeService.findById(id);
    }

    @PutMapping("/employee/{id}")
    public EmployeeDto update(@PathVariable long id, @RequestBody EmployeeUpdatedDto employeeUpdatedDto) {
        return employeeService.update(
                id, employeeUpdatedDto
        );
    }

    @DeleteMapping("/employee/{id}")
    public void delete(@PathVariable long id) {
        employeeService.delete(id);
    }

    @GetMapping("/employee")
    public List<EmployeeDto> list() {
        return employeeService.findAll();
    }

}
