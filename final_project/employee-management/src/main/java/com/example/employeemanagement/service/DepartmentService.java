package com.example.employeemanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.employeemanagement.dto.DepartmentDto;
import com.example.employeemanagement.dto.EmployeeDto;
import com.example.employeemanagement.exception.EntityAlreadyExistsException;
import com.example.employeemanagement.exception.EntityNotFoundException;
import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final EmployeeRepository employeeRepository;


    @Transactional
    public DepartmentDto create(String code, String name, String description, long managerId) {
        Optional<Department> optionalDepartment = departmentRepository.findById(code);
        if (optionalDepartment.isPresent()) {
            throw new EntityAlreadyExistsException("department with code=%s already exists".formatted(code));
        }

        long foundManagerId = employeeRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id=%d not found".formatted(managerId)))
                .getManagerId();

        Department department = new Department(code, name, description, foundManagerId);
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentDto.fromEntity(savedDepartment);
    }

    @Transactional(readOnly = true)
    public DepartmentDto findByCode(String code) {
        return departmentRepository.findById(code)
                .map(DepartmentDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Department with code=%s not found".formatted(code)));
    }

    @Transactional
    public DepartmentDto update(String code, String name, String description, long managerId) {
        Department department = departmentRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException("Department with code=%s not found".formatted(code)));
        long foundManagerId = employeeRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id=%d not found".formatted(managerId)))
                .getManagerId();
        department.setCode(code);
        department.setName(name);
        department.setDescription(description);
        department.setManagerId(foundManagerId);

        Department updatedDepartment = departmentRepository.save(department);
        return DepartmentDto.fromEntity(updatedDepartment);
    }

    @Transactional
    public void delete(String code) {
        departmentRepository.deleteById(code);
    }

    @Transactional(readOnly = true)
    public List<DepartmentDto> findAll() {
        return departmentRepository.findAll()
                .stream().map(DepartmentDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> findAllEmployeeInDepartment(String departmentCode) {
        Department department = departmentRepository.findById(departmentCode)
                .orElseThrow(
                        () -> new EntityNotFoundException("Department with code=%s not found".formatted(departmentCode))
                );
        return employeeRepository.findEmployeeByDepartment(department)
                .stream()
                .map(EmployeeDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public EmployeeDto findDepartmentManager(String departmentCode) {
        // todo
        return null;
    }

}
