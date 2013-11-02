package judger;

import models.Submit;

import java.util.List;

public class Judger {
    public static boolean running = false;
    public static void start() {
        if (!running) {
            running = true;
            List<Submit> submitList = Submit.find.where("status = 1").findList();
            if (submitList.size() > 0) {
                Runner runner = new Runner(submitList.get(0));
                runner.run();
                /*
                Akka.system().scheduler().scheduleOnce(
                        Duration.create(10, TimeUnit.MILLISECONDS),
                        runner,
                        Akka.system().dispatcher()
                );
                */
            } else {
                running = false;
            }
        }
    }
}
