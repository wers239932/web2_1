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
    public Result(PointWithScale point, boolean result, LocalDateTime timeSent, long timeWork) {
        this.point = point;
        this.result = result;
        this.timeSent = timeSent;
        this.timeWork = timeWork;
    }
    public String toString() {
        return point.toString() + " " + result + " " + timeSent + " " + timeWork;
    }
    public String getTime() {
        return timeSent.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
