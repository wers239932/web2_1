package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import requests.PointRequestWrapper;
import validation.PointWithScale;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


public class ControllerServlet extends HttpServlet {

    public void init() {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("doGet");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("doPost");
        InputStream inputStream = request.getInputStream();
        String err = "234";
        try {
            HashMap<?, ?> json = new ObjectMapper().readValue(inputStream, HashMap.class);
            System.out.println("json = " + json.entrySet());
            //response.sendError(HttpStatus.BAD_REQUEST.getCode(), json.keySet().toString());
                PointWithScale point = PointWithScale.getFromJson(json);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/check");
            rd.forward(new PointRequestWrapper(request, point), response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            response.sendError(response.SC_BAD_REQUEST, err);
        }

    }

    public void destroy() {
    }
}