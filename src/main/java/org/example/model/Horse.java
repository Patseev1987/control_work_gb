package org.example.model;

import java.time.LocalDate;
import java.util.List;

public class Horse extends Animal{
    public Horse(String name, String actions, LocalDate birthDate, AnimalType type) {
        super(name, actions, birthDate, type);
    }
}
