package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import requests.PointRequestWrapper;
import validation.PointWithScale;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;


public class ControllerServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("doGet");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("doPost");
        InputStream inputStream = request.getInputStream();
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = (ObjectNode) mapper.readTree(inputStream);
            ArrayList<PointWithScale> points;
            points = PointWithScale.getArrayFromJson(node.get("points"));
            for (PointWithScale point : points) {
                System.out.println(point.toString());
            }
            PointRequestWrapper requestWrapper;
            if (Objects.equals(node.get("update").asText(), "true")) {
                requestWrapper = new PointRequestWrapper(request, points, false);
            } else {
                requestWrapper = new PointRequestWrapper(request, points, true);
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/check");
            rd.forward(requestWrapper, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(response.SC_BAD_REQUEST);
        }

    }

}