package judger;

import java.util.ArrayList;
import java.util.List;

public class Judger {
    public static List<Submit> submits = new ArrayList<Submit>();

    public static void judge(Submit submit) {
        submits.add(submit);
        (new Thread(new Runner(submit))).start();
    }

    public static Submit findSubmitById(long id) {
        for (Submit s : submits) if (s.id == id) return s;
        return null;
    }

    public static void deleteSubmit(Submit submit) {
        submits.remove(submit);
    }
}
