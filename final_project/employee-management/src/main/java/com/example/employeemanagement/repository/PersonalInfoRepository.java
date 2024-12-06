package com.example.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.employeemanagement.model.PersonalInfo;

import java.util.List;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {

    @Query("""
            SELECT p FROM PersonalInfo p
            WHERE DATE_PART('day', p.birthdate) = date_part('day', CURRENT_DATE) 
              AND DATE_PART('month', p.birthdate) = date_part('month', CURRENT_DATE)
            """)
    List<PersonalInfo> findBirthdayToday();

    @Query("""
            SELECT p FROM PersonalInfo p
            WHERE DATE_PART('month', p.birthdate) = date_part('month', CURRENT_DATE)
            """)
    List<PersonalInfo> findBirthdayNextMonth();

}
