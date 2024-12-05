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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "office")
@Entity
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "address", nullable = false, length = 1024)
    private String address;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "description", nullable = true, length = 1024)
    private String description;

    public Office(String address, int capacity, String description) {
        this.address = address;
        this.capacity = capacity;
        this.description = description;
    }
}
