package org.example.model;

import java.time.LocalDate;
import java.util.List;

public class Camel extends Animal{
    public Camel(String name, String actions, LocalDate birthDate, AnimalType type) {
        super(name, actions, birthDate, type);
    }
}
