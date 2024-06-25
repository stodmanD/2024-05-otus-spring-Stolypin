package com.example.hw01.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AppProperties implements FileNameProvider {
    private String testFileName;

    @Override
    public String getFileName() {
        return testFileName;
    }
}
