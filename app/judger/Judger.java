package judger;

import java.util.List;

public class Judger {
    public static List<Submit> submits;

    public static void judge(Submit submit) {
        (new Thread(new Runner(submit))).start();
    }

    /*
    public static void start() {

        if (!running) {
            running = true;
            List<Submit> submitList = Submit.find.where("status = 1").findList();
            if (submitList.size() > 0) {
                Runner runner = new Runner(submitList.get(0));
                Thread thread = new Thread(runner);
                thread.run();
                /*
                Akka.system().scheduler().scheduleOnce(
                        Duration.create(10, TimeUnit.MILLISECONDS),
                        runner,
                        Akka.system().dispatcher()
                );
            } else {
                running = false;
            }
        }
    }
    */

    public static Submit findSubmitById(long id) {
        for (Submit s : submits) if (s.id == id) return s;
        return null;
    }
}
