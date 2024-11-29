package com.example.beans;

import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.text.SimpleDateFormat;
import java.util.Date;

@Named
@ManagedBean
@RequestScoped
public class DateBean {
    private Date currentDate;

    public DateBean(){
        currentDate = new Date();
    }

    public String getCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(currentDate);
    }
}
