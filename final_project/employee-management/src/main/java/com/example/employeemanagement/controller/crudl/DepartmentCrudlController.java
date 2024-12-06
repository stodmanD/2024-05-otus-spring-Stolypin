package com.example.employeemanagement.controller.crudl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.employeemanagement.dto.DepartmentUpdateDto;
import com.example.employeemanagement.dto.DepartmentDto;
import com.example.employeemanagement.service.DepartmentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentCrudlController {

    private final DepartmentService departmentService;


    @PostMapping("/department/{code}")
    public DepartmentDto create(@PathVariable String code, @RequestBody DepartmentUpdateDto departmentUpdateDto) {
        return departmentService.create(
                code.toUpperCase(),
                departmentUpdateDto.name(),
                departmentUpdateDto.description(),
                departmentUpdateDto.managerId()
        );
    }

    @GetMapping("/department/{code}")
    public DepartmentDto read(@PathVariable String code) {
        return departmentService.findByCode(code.toUpperCase());
    }

    @PutMapping("/department/{code}")
    public DepartmentDto update(@PathVariable String code, @RequestBody DepartmentUpdateDto departmentUpdateDto) {
        return departmentService.update(
                code.toUpperCase(),
                departmentUpdateDto.name(),
                departmentUpdateDto.description(),
                departmentUpdateDto.managerId()
        );
    }

    @DeleteMapping("/department/{code}")
    public void delete(@PathVariable String code) {
        departmentService.delete(code.toUpperCase());
    }

    @GetMapping("/department")
    public List<DepartmentDto> list() {
        return departmentService.findAll();
    }

}
