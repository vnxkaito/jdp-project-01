package com.ga.java.project01;

import java.util.ArrayList;
import java.util.List;

public class Person {
    static String fileName = "persons.csv";

    String personId;
    String CPR;
    String firstName;
    String lastName;
    int age;
    String email;

    public Person(String personId, String CPR, String firstName, String lastName, int age, String email){
        this.personId = personId;
        this.CPR = CPR;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    protected static Person getPerson(String personId){
        List<Person> persons = getAllPersons();
        for(Person p : persons){
            if(p.personId.equalsIgnoreCase(personId)) return p;
        }
        return null;
    }

    protected static boolean createPerson(Person p){
        if(getPerson(p.personId) != null){
            System.out.println("Person already exists");
            return false;
        }
        List<Person> list = new ArrayList<>();
        list.add(p);
        CSVHandler.appendToCSVFile(fileName, toCSVData(list));
        return true;
    }

    protected boolean update(){
        List<Person> persons = getAllPersons();
        boolean found = false;
        for(int i = 0; i < persons.size(); i++){
            if(persons.get(i).personId.equalsIgnoreCase(this.personId)){
                persons.set(i, this);
                found = true;
                break;
            }
        }
        if(found){
            CSVHandler handler = new CSVHandler();
            handler.writeToCSVFile(fileName, toCSVData(persons));
        }
        return found;
    }

    protected boolean delete(){
        List<Person> persons = getAllPersons();
        boolean removed = persons.removeIf(p -> p.personId.equalsIgnoreCase(this.personId));
        if(removed){
            CSVHandler handler = new CSVHandler();
            handler.writeToCSVFile(fileName, toCSVData(persons));
        }
        return removed;
    }

    protected static List<Person> getAllPersons(){
        List<List<String>> data = CSVHandler.readCSV(fileName);
        List<Person> persons = new ArrayList<>();
        if(data != null){
            for(List<String> row : data){
                persons.add(new Person(row.get(0), row.get(1), row.get(2), row.get(3),
                        Integer.parseInt(row.get(4)), row.get(5)));
            }
        }
        return persons;
    }

    protected static List<List<String>> toCSVData(List<Person> persons){
        List<List<String>> data = new ArrayList<>();
        for(Person p : persons){
            List<String> row = new ArrayList<>();
            row.add(p.personId);
            row.add(p.CPR);
            row.add(p.firstName);
            row.add(p.lastName);
            row.add(String.valueOf(p.age));
            row.add(p.email);
            data.add(row);
        }
        return data;
    }
}
