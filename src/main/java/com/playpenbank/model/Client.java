package com.playpenbank.model;

public class Client extends User { // Will implement UserActions interface SOMEDAY

    public Client(int id, String dni, String password, String name, String lastName) {
        super(id, dni, password, name, lastName, "client");
    }
}
