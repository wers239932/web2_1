package requests;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import validation.PointWithScale;

import java.util.ArrayList;


public class PointRequestWrapper extends HttpServletRequestWrapper {
    private final ArrayList<PointWithScale> points;
    public Boolean getResults;

    public PointRequestWrapper(HttpServletRequest request, ArrayList<PointWithScale> points, Boolean results) {
        super(request);
        this.points = points;
        this.getResults=results;
    }

    public ArrayList<PointWithScale> getPoints() {return points;}
}
