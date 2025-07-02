package com.playpenbank.model;

public class Employee extends User {

    public Employee(int id, String dni, String password, String name, String lastName) {
        super(id, dni, password, name, lastName, "employee");
    }
}