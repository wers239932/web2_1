package beans;

import java.beans.Beans;
import java.util.ArrayList;

public class History extends Beans {
    private ArrayList<Result> history = new ArrayList<>();

    public History() {
        this.history = new ArrayList<>();
    }

    public void add(Result result) {
        this.history.add(result);
    }

    public ArrayList<Result> getHistory() {
        return history;
    }
}
