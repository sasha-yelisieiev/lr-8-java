package com.example.calculation;

import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

import java.io.Serializable;

@SessionScoped
@Named
public class CalculationBean implements Serializable {
    private int initialValue = 0;

    @Produces
    private int currentResult = initialValue;

    // Метод для отримання поточного результату
    public int getResult() {
        return currentResult;
    }

    public void add(int value) {
        currentResult += value;
    }

    public void subtract(int value) {
        currentResult -= value;
    }
}
