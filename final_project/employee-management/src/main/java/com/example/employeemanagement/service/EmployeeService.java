package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeUpdatedDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.employeemanagement.dto.EmployeeDto;
import com.example.employeemanagement.exception.EntityNotFoundException;
import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.Office;
import com.example.employeemanagement.model.PersonalInfo;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.repository.OfficeRepository;
import com.example.employeemanagement.repository.PersonalInfoRepository;
import com.example.employeemanagement.service.http.AccountProviderHttpClient;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final PersonalInfoRepository personalInfoRepository;

    private final DepartmentRepository departmentRepository;

    private final OfficeRepository officeRepository;

    private final AccountProviderHttpClient accountProviderHttpClient;


    @Transactional
    public EmployeeDto create(EmployeeUpdatedDto employeeUpdatedDto) {

        Employee employee = Employee.builder()
                .personalInfo(personalInfoById(employeeUpdatedDto.personalInfoId()))
                .jobTitle(employeeUpdatedDto.jobTitle())
                .managerId(employeeUpdatedDto.managerEmployeeId())
                .department(departmentByCodeOrNull(employeeUpdatedDto.departmentCode()))
                .office(officeByIdOrNull(employeeUpdatedDto.officeId()))
                .additionalNumber(employeeUpdatedDto.additionalNumber())
                .accountId(employeeUpdatedDto.accountId())
                .build();

        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeDto.fromEntity(savedEmployee);
    }

    @Transactional(readOnly = true)
    public EmployeeDto findById(long id) {
        return employeeRepository.findById(id)
                .map(EmployeeDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id=%d not found".formatted(id)));
    }

    @Transactional
    public EmployeeDto update(long id, EmployeeUpdatedDto employeeUpdatedDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id=%d not found".formatted(id)));

        employee.setPersonalInfo(personalInfoById(employeeUpdatedDto.personalInfoId()));
        employee.setJobTitle(employeeUpdatedDto.jobTitle());
        employee.setManagerId(employeeUpdatedDto.managerEmployeeId());
        employee.setDepartment(departmentByCodeOrNull(employeeUpdatedDto.departmentCode()));
        employee.setOffice(officeByIdOrNull(employeeUpdatedDto.officeId()));
        employee.setAdditionalNumber(employeeUpdatedDto.additionalNumber());
        employee.setAccountId(employeeUpdatedDto.accountId());

        Employee updatedEmployee = employeeRepository.save(employee);
        return EmployeeDto.fromEntity(updatedEmployee);
    }

    @Transactional
    public void delete(long id) {
        String accountId = employeeByIdOrNull(id).getAccountId();
        accountProviderHttpClient.deleteRequest(accountId);
        employeeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> findAll() {
        return employeeRepository.findAll()
                .stream().map(EmployeeDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public EmployeeDto findManagerById(long id) {
        Long managerId = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id=%d not found".formatted(id)))
                .getManagerId();

        if (managerId == null) {
            return null;
        }
        Employee managerEmployee = employeeRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id=%d not found".formatted(managerId)));
        return EmployeeDto.fromEntity(managerEmployee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> findAllInOffice(long officeId) {
        return employeeRepository.findEmployeeByOfficeId(officeId)
                .stream()
                .map(EmployeeDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> findAllRemote() {
        return employeeRepository.findEmployeeWhereOfficeIdIsNull()
                .stream()
                .map(EmployeeDto::fromEntity)
                .toList();
    }


    private PersonalInfo personalInfoById(long id) {
        return personalInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PersonalInfo with id=%d not found".formatted(id)));
    }

    private Employee employeeByIdOrNull(Long id) {
        if (id != null) {
            return employeeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Employee with id=%d not found".formatted(id)));
        } else {
            return null;
        }
    }

    private Department departmentByCodeOrNull(String code) {
        if (code != null) {
            return departmentRepository.findById(code)
                    .orElseThrow(() -> new EntityNotFoundException("Department with code=%s not found".formatted(code)));
        } else {
            return null;
        }
    }

    private Office officeByIdOrNull(Long id) {
        if (id != null) {
            return officeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Office with id=%d not found".formatted(id)));
        } else {
            return null;
        }
    }

}
