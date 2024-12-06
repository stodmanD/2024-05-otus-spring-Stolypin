package com.example.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.employeemanagement.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> { }
