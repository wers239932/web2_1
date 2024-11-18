package servlets;

import beans.History;
import beans.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import requests.PointRequestWrapper;
import validation.PointWithScale;
import validation.Validator;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class AreaCheckServlet extends HttpServlet {

    public void init() {

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long startTime = System.nanoTime();
        try {
            PointRequestWrapper req = (PointRequestWrapper) request;
            History history = (History) req.getSession().getAttribute("history");
            if (history==null) {
                history = new History();
                req.getSession().setAttribute("history", history);
            }
            PointWithScale point = req.getPoint();
            Boolean fitsIn = Validator.check(point);
            Result result = new Result(point, fitsIn, LocalDateTime.now(), System.nanoTime() - startTime);
            history.add(result);
            req.getSession().setAttribute("history", history);
            for(Result result1 : ((History) req.getSession().getAttribute("history")).getHistory()) {
                System.out.println(result1);
            }

            System.out.println("doCheck");

            response.sendRedirect("http://localhost:8080/web2_1-1.0-SNAPSHOT/results.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


    }

    public void destroy() {
    }
}