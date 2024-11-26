package servlets;

import beans.History;
import beans.Result;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import requests.PointRequestWrapper;
import validation.PointWithScale;
import validation.Validator;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AreaCheckServlet extends HttpServlet {

    public void init() {

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        long startTime = System.nanoTime();
        try {
            PointRequestWrapper req = (PointRequestWrapper) request;
            History history = (History) req.getSession().getAttribute("history");
            if (history == null) {
                history = new History();
                req.getSession().setAttribute("history", history);
            }
            ArrayList<PointWithScale> points = req.getPoints();

            for(PointWithScale point : points) {
                if(Validator.validate(point)) {
                    Boolean fitsIn = Validator.check(point);
                    Result result = new Result(point, fitsIn, LocalDateTime.now(), System.nanoTime() - startTime);
                    history.add(result);
                }
            }

            req.getSession().setAttribute("history", history);
//            for (Result result1 : ((History) req.getSession().getAttribute("history")).getHistory()) {
//                System.out.println(result1);
//            }

            System.out.println("doCheck");
            if(req.getResults) {
                for(PointWithScale point : points) {
                    if(Validator.validate(point)) {
                        Boolean fitsIn = Validator.check(point);
                        Result result = new Result(point, fitsIn, LocalDateTime.now(), System.nanoTime() - startTime);
                        history.add(result);
                    }
                }

                req.getSession().setAttribute("history", history);
                response.sendRedirect("http://localhost:8080/web2_1-1.0-SNAPSHOT/results.jsp");
            } else {
                PrintWriter out = response.getWriter();
                ArrayList<Result> results = new ArrayList<>();
                for(PointWithScale point : points) {
                    if(Validator.validate(point)) {
                        Boolean fitsIn = Validator.check(point);
                        Result result = new Result(point, fitsIn, LocalDateTime.now(), System.nanoTime() - startTime);
                        results.add(result);
                        history.add(result);
                    }
                }
                req.getSession().setAttribute("history", history);
                new ObjectMapper().writeValue(out, results);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

}