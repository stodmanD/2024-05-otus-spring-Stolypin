package com.example.employeemanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "employee")
@Builder
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "personal_info_id", nullable = false)
    private PersonalInfo personalInfo;

    @Column(name = "job_title", nullable = false, length = 255)
    private String jobTitle;

    @Column(name = "manager_id", nullable = true)
    private Long managerId;

    @ManyToOne
    @JoinColumn(name = "department_code", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "office_id", nullable = true)
    private Office office;

    @Column(name = "additional_number", nullable = false)
    private Integer additionalNumber;

    @Column(name = "account_id", nullable = true, length = 64)
    private String accountId;

}
