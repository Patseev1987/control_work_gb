package org.example;

import org.example.dao.DataAccess;
import org.example.menu.Menu;
import org.example.model.AnimalType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {

        DataAccess dataAccess = new DataAccess();
       dataAccess.showAllAnimal();


        System.out.println(AnimalType.CAT.getType());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        System.out.println(LocalDate.parse("12.04.2014",formatter));
        AnimalType d = AnimalType.DOG;
        System.out.println(d.getType());

     //   Animal a = AnimalFactory.getAnimal("Бобик","ест, пьет",LocalDate.parse("12.04.2014",formatter),d.getType());
     //   dataAccess.createNewAnimal(a);
        Menu menu = new Menu();
        menu.start();


        String [] fff = "asdsad, sdsdf ,dsf ".split("\\p{P}");
        for (String st:fff
             ) {
            System.out.println(st);
        }
    }
}