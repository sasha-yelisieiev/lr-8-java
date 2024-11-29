package com.example.beans;

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.io.Serializable;

@Singleton
@Startup
public class UserCounter implements Serializable {
    private int activeUsers = 0;

    public void incrementActiveUsers() {
        activeUsers++;
    }

    public void decrementActiveUsers() {
        if (activeUsers > 0) {
            activeUsers--;
        }
    }

    public int getActiveUsers() {
        return activeUsers;
    }
}
