package com.example.employeemanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "personal_info")
@Entity
public class PersonalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "employment_date", nullable = false)
    private LocalDate employmentDate;

    @Column(name = "is_man", nullable = false)
    private boolean isMan;

    public PersonalInfo(String fullName, LocalDate birthdate, LocalDate employmentDate, boolean isMan) {
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.employmentDate = employmentDate;
        this.isMan = isMan;
    }
}
