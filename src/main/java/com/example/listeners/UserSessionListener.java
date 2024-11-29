package com.example.listeners;

import com.example.beans.UserCounter;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class UserSessionListener implements HttpSessionListener {
    @EJB
    private UserCounter userCounter;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        userCounter.incrementActiveUsers();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        userCounter.decrementActiveUsers();
    }
}
