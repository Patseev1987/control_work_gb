package org.example.model;

import java.time.LocalDate;


public class AnimalFactory {
    private AnimalFactory(){};

    public static Animal getAnimal(String name , String actions, LocalDate birth_date, String type) {
        switch (type) {
            case "кошки" -> {
                return new Cat(name, actions, birth_date, AnimalType.CAT);
            }
            case "собаки" -> {
                return new Dog(name, actions, birth_date, AnimalType.DOG);
            }
            case "хомяки" -> {
                return new Horse(name, actions, birth_date, AnimalType.HAMSTER);
            }
            case "ослы" -> {
                return new Donkey(name, actions, birth_date, AnimalType.DONKEY);
            }
            case "лошади" -> {
                return new Hamster(name, actions, birth_date, AnimalType.HORSE);
            }
        }
        return null;
    }
}
