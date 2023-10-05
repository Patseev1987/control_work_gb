package org.example.model;

import java.time.LocalDate;
import java.util.List;

public class Cat extends Animal{
    public Cat(String name, String actions, LocalDate birthDate, AnimalType type) {
        super(name, actions, birthDate, type);
    }
}
