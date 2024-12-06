package com.example.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.employeemanagement.model.Office;

public interface OfficeRepository extends JpaRepository<Office, Long> {

}
