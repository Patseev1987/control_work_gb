package org.example.model;

public enum AnimalType {

    CAT("кошки"),
    DOG("собаки"),
    HAMSTER("хомяки"),
    HORSE("лошади"),
    DONKEY("ослы");

    private String type;
    AnimalType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
