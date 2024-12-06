package com.example.accountprovider.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HexFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "accounts")
public class Account {

    @Id
    private String id;

    private String login;

    private String password;

    private LocalDate dateTillPassword;

    private String email;

    private Boolean active;


    @Override
    public String toString() {
        return "Account{" +
               "id='" + id + '\'' +
               ", login='" + login + '\'' +
               ", hexPassword='" + HexFormat.of().formatHex(password.getBytes()) + '\'' +
               ", dateTillPassword=" + dateTillPassword +
               ", email='" + email + '\'' +
               ", active=" + active +
               '}';
    }

}
