package com.example.beans;

import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;

import java.io.Serializable;


@Named
@ManagedBean
@SessionScoped
public class MessageBean implements Serializable {
    private String message = "Default message";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void validateMessage(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String str = (String) value;
        if (str.length() < 3) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Message is too short", "Message must be at least 3 characters long"));
        }
    }
}
