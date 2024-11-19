package requests;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import validation.PointWithScale;


public class PointRequestWrapper extends HttpServletRequestWrapper {
    private final PointWithScale point;

    public PointRequestWrapper(HttpServletRequest request, PointWithScale point) {
        super(request);
        this.point = point;
    }

    public PointWithScale getPoint() {
        return point;
    }
}
