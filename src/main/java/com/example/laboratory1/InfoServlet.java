package com.example.laboratory1;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "infoServlet", urlPatterns = {"/info-servlet"})
public class InfoServlet extends HttpServlet {
    private Map<String, String> dataStore = new HashMap<>();

    @Override
    public void init(){}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Request Information</h1>");
        out.println("<p>Method: " + request.getMethod() + "</p>");
        out.println("<p>URL: " + request.getRequestURL() + "</p>");
        out.println("<p>Protocol: " + request.getProtocol() + "</p>");
        out.println("<h2>Stored Data:</h2>");
        for (Map.Entry<String, String> entry : dataStore.entrySet()) {
            out.println("<p>" + entry.getKey() + ": " + entry.getValue() + "</p>");
        }
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();

        String[] parts = data.split(",");
        String key = null;
        String value = null;
        for (String part : parts) {
            String[] keyValue = part.split(":");
            if (keyValue.length == 2) {
                if (keyValue[0].contains("key")) {
                    key = keyValue[1].replaceAll("[\"{}]", "").trim();
                } else if (keyValue[0].contains("value")) {
                    value = keyValue[1].replaceAll("[\"{}]", "").trim();
                }
            }
        }

        if (key != null && value != null) {
            dataStore.put(key, value);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println("{\"message\": \"Data Added. Key: " + key + ", Value: " + value + "\"}");
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Key and value are required");
        }
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

        String[] parts = data.split(",");
        String key = null;
        String value = null;
        for (String part : parts) {
            String[] keyValue = part.split(":");
            if (keyValue.length == 2) {
                if (keyValue[0].contains("key")) {
                    key = keyValue[1].replaceAll("[\"{}]", "").trim();
                } else if (keyValue[0].contains("value")) {
                    value = keyValue[1].replaceAll("[\"{}]", "").trim();
                }
            }
        }

        if (key != null && value != null) {
            if (dataStore.containsKey(key)) {
                dataStore.put(key, value);
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.println("{\"message\": \"Data Updated. Key: " + key + ", New Value: " + value + "\"}");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Key not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid data format");
        }
    }

    @Override
    public void destroy(){}
}
