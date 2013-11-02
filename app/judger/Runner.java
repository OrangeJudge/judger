package judger;

import models.Submit;

public class Runner extends Thread {
    public Submit submit;

    public Runner(Submit submit) {
        this.submit = submit;
    }

    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        submit.status = 2;
        submit.save();
        System.out.println("ran " + submit.id);
        Judger.running = false;
        Judger.start();
    }

    public void compile() {

    }
}
