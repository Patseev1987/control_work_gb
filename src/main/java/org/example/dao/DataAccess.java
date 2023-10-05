package org.example.dao;

import org.example.model.Animal;
import org.example.model.AnimalFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataAccess {
    private static Properties myProperties = new Properties();
    private static String url;
    private static String user;
    private static String password;

    private static Connection connection;

    static {
        try {
            myProperties.load(new FileReader(new File("DataBase.properties")));
            url = myProperties.getProperty("url");
            user = myProperties.getProperty("user");
            password = myProperties.getProperty("password");
            connection = DriverManager.getConnection(url, user, password);
        } catch (IOException e) {
            System.out.println("Файл с настройками не найден");
        } catch (SQLException e) {
            System.out.println("База даных не найдена");
        }
    }

    public boolean updateActions(String action, Animal animal) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE все_животные SET name=?,actions=?,birth_date=?,animal_group=? WHERE id=?");
            preparedStatement.setInt(5, animal.getId());
            preparedStatement.setString(1, animal.getName());
            preparedStatement.setString(2, animal.getActions() + ", " + action);
            preparedStatement.setDate(3, Date.valueOf(animal.getBirthDate()));
            preparedStatement.setString(4, animal.getType().getType());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }


    public boolean createNewAnimal(Animal animal) {
        if (animal == null) {
            return false;
        } else {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO все_животные (name,actions,birth_date,animal_group) VALUES (?,?,?,?)");
                preparedStatement.setString(1, animal.getName());
                preparedStatement.setString(2, animal.getActions());
                preparedStatement.setDate(3, Date.valueOf(animal.getBirthDate()));
                preparedStatement.setString(4, animal.getType().getType());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                return false;
            }

        }
        return true;
    }

    public Animal getAnimal(int id) {
        Animal animal = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM все_животные WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                animal = AnimalFactory.getAnimal(resultSet.getString("name"), resultSet.getString("actions"), resultSet.getDate("birth_date").toLocalDate(), resultSet.getString("animal_group"));
            }
        } catch (SQLException e) {
            System.out.println("Сбой базы данных");
        }
        return animal;
    }

    public void showAllAnimal() {
        List<Animal> animals = getAllAnimal();
        for (Animal animal : animals) {
            System.out.println(animal);
            System.out.println();
        }

    }

    private List<Animal> getAllAnimal() {
        List<Animal> animals = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM все_животные ");
            while (resultSet.next()) {
                Animal newAnimal = AnimalFactory.getAnimal(resultSet.getString("name"), resultSet.getString("actions"), resultSet.getDate("birth_date").toLocalDate(), resultSet.getString("animal_group"));
                newAnimal.setId(resultSet.getInt("id"));
                animals.add(newAnimal);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка базы данных");
        }
        return animals;
    }

    public int getCountAnimals() {
        int result = 1;
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT COUNT(*) FROM все_животные");
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            result = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}



