package com.example.hw14spring_integration.models;

public class Beginner extends AbstractHuman {
    public Beginner(String name) {
        super(name);
    }

    private Beginner(String name, boolean readyToCoding) {
        super(name, readyToCoding);
    }

    public static Beginner createCoding(String name) {
        return new Beginner(name, true);
    }
}
