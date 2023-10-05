package org.example.model;

import java.time.LocalDate;
import java.util.List;

public class Dog extends Animal{
    public Dog(String name, String actions, LocalDate birthDate, AnimalType type) {
        super(name, actions, birthDate, type);
    }
}
