package com.playpenbank.model;// src/test/java/com/playpenbank/model/PlaypenATMTest.java
import com.playpenbank.model.PlaypenATM;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class
PlaypenATMTest {

    @Test
    void testGetBalance() {
        PlaypenATM atm = PlaypenATM.getInstance();
        double balance = atm.getBalance();
        assertTrue(balance >= 0, "El saldo del cajero debe ser mayor o igual a cero");
    } // Simple test to check if the ATM balance is non-negative!
}