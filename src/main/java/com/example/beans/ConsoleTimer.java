package com.example.beans;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Startup
public class ConsoleTimer {
    private List<String> messages = new ArrayList<>();
    private static final int MAX_MESSAGES = 10;

    @Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)
    public void automaticTimeout() {
        String message = "Automatic timeout occurred at " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        messages.add(message);
        if (messages.size() > MAX_MESSAGES) {
            messages.remove(0);
        }
        System.out.println(message);
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }
}
