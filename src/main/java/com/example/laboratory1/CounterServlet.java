package com.example.laboratory1;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "counterServlet", urlPatterns = {"/counter-servlet"})
public class CounterServlet extends HttpServlet {
    private int counter = 0;

    @Override
    public void init(){}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Counter: " + counter + "</h1>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        counter++;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Counter increased. New value: " + counter + "</h1>");
        out.println("</body></html>");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();

        String[] parts = data.split(":");
        if (parts.length == 2 && parts[0].contains("value")) {
            try {
                counter = Integer.parseInt(parts[1].replaceAll("[^0-9]", ""));
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.println("{\"message\": \"Counter set to: " + counter + "\"}");
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid value format");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid data format");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        counter = 0;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Counter reset to 0</h1>");
        out.println("</body></html>");
    }

    @Override
    public void destroy() {
        System.out.println("CounterServlet destroyed. Final count: " + counter);
    }
}
