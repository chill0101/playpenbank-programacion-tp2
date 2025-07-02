// src/main/java/com/playpenbank/service/EmployeeService.java
package com.playpenbank.service;

import com.playpenbank.model.PlaypenATM;

public class EmployeeService {
    public void cargarATM(double amount) {
        PlaypenATM.getInstance().fill(amount);
    } // sorry for the spanglish, was a mistake in the name of the method
}