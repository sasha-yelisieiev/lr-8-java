package com.example.laboratory1;

import java.io.*;
import java.time.LocalDateTime;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "timeServlet", urlPatterns = {"/time-servlet"})
public class TimeServlet extends HttpServlet {
    @Override
    public void init() {
        System.out.println("TimeServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Current time: " + LocalDateTime.now() + "</h1>");
        out.println("</body></html>");
    }

    @Override
    public void destroy(){}
}
