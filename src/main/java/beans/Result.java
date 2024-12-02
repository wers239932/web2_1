package beans;

import validation.PointWithScale;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Result(
        PointWithScale point,
        boolean result,
        LocalDateTime timeSent,
        long timeWork
) {

    public String toString() {
        return point.toString() + " " + result + " " + timeSent + " " + timeWork;
    }

    public String getTime() {
        return timeSent.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
