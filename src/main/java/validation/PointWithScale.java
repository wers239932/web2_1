package validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

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



    public static ArrayList<PointWithScale> getArrayFromJson(JsonNode node) {
        ArrayList<PointWithScale> array = new ArrayList<>();
        for (JsonNode entry : node) {
            PointWithScale point = new PointWithScale(entry.get("X").asDouble(), entry.get("Y").asDouble(), entry.get("R").asDouble());
            array.add(point);
        }
        return array;
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
