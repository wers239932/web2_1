package validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;

public class PointWithScale {
    public PointWithScale(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Validatable(min = -5, max = 3)
    public final double x;
    @Validatable(min = -5, max = 3)
    public final double y;
    @Validatable(min = 1, max = 4)
    public final double r;

    public static PointWithScale parse(String x, String y, String r) throws NumberFormatException {
            return new PointWithScale(Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(r));
    }

    public void addToJson(ObjectNode json) {
        json.put("X", this.x);
        json.put("Y", this.y);
        json.put("R", this.r);
    }

    public static PointWithScale getFromJson(HashMap vars)throws NumberFormatException {
        return parse(vars.get("X").toString(), vars.get("Y").toString(), vars.get("R").toString());
    }

    @SuppressWarnings("unused")
    public JsonNode toJsonNode() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.valueToTree(this);
    }

    public String getX() {
        return Double.toString(x);
    }

    public String getY() {
        return Double.toString(y);
    }

    public String getR() {
        return Double.toString(r);
    }

    public String toString() {
        return "PointWithScale [x=" + x + ", y=" + y + ", r=" + r + "]";
    }
}
