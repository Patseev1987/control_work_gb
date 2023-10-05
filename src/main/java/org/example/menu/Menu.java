package org.example.menu;

import org.example.dao.DataAccess;
import org.example.exceptions.OpenCounterException;
import org.example.model.Animal;
import org.example.model.AnimalFactory;
import org.example.model.AnimalType;
import org.example.model.CounterNew;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private boolean isRepeat = true;
    private DataAccess dataAccess = new DataAccess();

    public void start() {
        while (isRepeat) {
            System.out.println("""
                    Главное меню
                    1. Внести в базу новое животное.
                    2. Показать что делает каждое животное.
                    3. Добавить новые действия у животного.
                    4. Выход из программы""");
            String choice = scanner.next();
            switch (choice) {
                case "1": {
                    if (addNewAnimal()) {
                            System.out.println("Животное успешно добавленно!");
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        System.out.println("Всего животных в списке --- "+CounterNew.getCount());
                        }
                    break;
                }
                case "2": {
                    dataAccess.showAllAnimal();
                    break;
                }
                case "3": {
                    if(updateActions()){
                        System.out.println("Действие добавлено успешно");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        System.out.println("Произошла ошибка. Попробуйте еще раз.");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                }
                case "4": {
                    isRepeat = false;
                    break;
                }
                default: {
                    System.out.println("Вы ввели неверный номер команды\nПопробуйте еще раз");
                }
            }
        }
    }

    private boolean checkNull(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean addNewAnimal() {

        try (CounterNew counter = new CounterNew()) {
            System.out.println("Введите кличку животного:");
            scanner.nextLine();
            String name = scanner.next();
            if (checkNull(name)) {
                System.out.println("Вы ввели неверное имя");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return false;
            }

            System.out.println(name);


            System.out.println("Введите что может делать животное через знак \",\":");
            scanner.nextLine();
            String actions = scanner.nextLine();
            if (checkNull(actions)) {
                System.out.println("Вы ввели неверные действия для животного");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return false;
            }

            System.out.println(actions);


            System.out.println("""
                    Введите дату рождения животного в формате дд.мм.гггг:""");
            String birthDate = scanner.nextLine();
            if (!(checkBirthDate(birthDate))) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return false;
            }

            System.out.println(birthDate);

            String type = getAnimalType();
            if (type == null) {
                System.out.println("Вы ввели неверный тип животного");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println(type);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            Animal animal = AnimalFactory.getAnimal(name, actions, LocalDate.parse(birthDate, formatter), type);
            if (!(dataAccess.createNewAnimal(animal))) {
                System.out.println("Произошла ошибка при работе с базой данных");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return false;
            }
            counter.add();
            System.out.println("Количество животных в базе  --- "+CounterNew.getCount());
            return true;

        } catch (OpenCounterException e) {
            System.out.println(e.getMsg());
            return false;
        }
    }

        private String getAnimalType() {
        String type;
        System.out.println("Выберите тип животного из списка:");
        System.out.println("""
                1. Кошка или кот
                2. Собака
                3. Хомяк
                4. Лошадь
                5. Осел""");
        String animal = scanner.next();
        switch (animal) {
            case "1":
                type = AnimalType.CAT.getType();
                break;
            case "2":
                type = AnimalType.DOG.getType();
                break;
            case "3":
                type = AnimalType.HAMSTER.getType();
                break;
            case "4":
                type = AnimalType.HORSE.getType();
                break;
            case "5":
                type = AnimalType.DONKEY.getType();
                break;
            default:
                type = null;
        }
        return type;
    }

    private boolean updateActions() {
        System.out.println("Введите id животного, которого хотите обучить\nновым действиям:");
        int id = -1;
        String action = null;
        try {
            id = scanner.nextInt();
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат id");
            return false;
        }
        Animal animal = dataAccess.getAnimal(id);
        animal.setId(id);
        System.out.println("Введите новое действие, которому хотите обучить животное по кличке - " + animal.getName());
        action = scanner.next();
        if (animal != null) {
           if(dataAccess.updateActions(action,animal)){
               return true;
           }else{
               return false;
           }
        }else{
            return false;
        }
    }

    private boolean checkBirthDate(String birthDate) {
        // датарождения - строка формата dd.mm.yyyy
        Pattern checkBirthDatePattern = Pattern.compile("\\d{1,2}\\.\\d{1,2}\\.\\d{4}");
        Matcher matcher = checkBirthDatePattern.matcher(birthDate);
        if (matcher.matches()) {
            String[] temp = birthDate.split("\\.");
            if (temp[1].length() != 2 || ((Integer.parseInt(temp[1]) < 1
                    || (Integer.parseInt(temp[1]) > 13)))) {
                System.out.println("Вы ввели не правльный месяц - " + birthDate);
                return false;
            }

            if (temp[2].length() != 4) {
                System.out.println("Вы ввели не правльный год - " + birthDate);
                return false;
            }
            int amountDaysInTheYear = Year.of(Integer.parseInt(temp[2])).length();
            int lastDayInMonth = amountDaysInTheYear == 366 ? Month.of(Integer.parseInt(temp[1])).maxLength() : Month.of(Integer.parseInt(temp[1])).minLength();
            if (temp[0].length() != 2 || ((Integer.parseInt(temp[0]) > lastDayInMonth)
                    || (Integer.parseInt(temp[0]) < 1))) {

                System.out.println("Вы ввели не правльный день - " + birthDate);
                return false;
            }
        } else {
            System.out.println("Вы ввели дату в неправильном формате (дд.мм.гггг) - " + birthDate);
            return false;
        }
        return true;
    }
}
