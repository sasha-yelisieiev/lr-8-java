package com.example.controller;

import com.example.beans.ConsoleTimer;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class TimerController implements Serializable {
    @EJB
    private ConsoleTimer consoleTimer;

    public List<String> getTimerMessages() {
        return consoleTimer.getMessages();
    }
}