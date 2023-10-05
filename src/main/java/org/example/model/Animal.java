package org.example.model;


import java.time.LocalDate;


abstract public class Animal {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private String actions;
    private LocalDate birthDate;
    private AnimalType type;

    public Animal(String name, String actions, LocalDate birthDate, AnimalType type) {
        this.name = name;
        this.actions = actions;
        this.birthDate = birthDate;
        this.type = type;
    }

    public String getName() {
        return name;
    }



    public String getActions() {
        return actions;
    }



    public LocalDate getBirthDate() {
        return birthDate;
    }



    public AnimalType getType() {
        return type;
    }



    @Override
    public String toString() {
        return "id животного --- "+id+"\nВходит в группу " + type.getType() + "\n"
                + getSingleType(type) + " по имени " + name +
                "\n" + showActions();
    }

    private String showActions() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] result = actions.split("\\p{P}");
        for (int i = 0; i < result.length; i++) {
            stringBuilder.append(String.format("%d. %s\n", i + 1, result[i].trim()));
        }
        return stringBuilder.toString();
    }

    private String getSingleType(AnimalType type) {
        switch (type) {
            case HAMSTER -> {
                return "хомяк";
            }
            case DONKEY -> {
                return "осел";
            }
            case HORSE -> {
                return "лошадь";
            }
            case CAT -> {
                return "кошка или кот";
            }
            case DOG -> {
                return "собака";
            }
        }
        return "";
    }
}

