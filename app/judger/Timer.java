package judger;

/*
 * Modified from
 * https://github.com/sankha93/codejudge/blob/master/codejudge-compiler/src/codejudge/compiler/TimedShell.java
 */

import judger.languages.Language;

public class Timer extends Thread {

    Language language;
    Process process;
    long timeLimit;

    public Timer(Language language, Process process, long timeLimit){
        this.language = language;
        this.process = process;
        this.timeLimit = timeLimit;
    }

    // Sleep until timeout and then terminate the process
    public void run() {
        try {
            System.out.println("sleep " + timeLimit);
            sleep(timeLimit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            language.timeLimitExceeded = false;
            process.exitValue();
        } catch (IllegalThreadStateException e) {
            language.timeLimitExceeded = true;
            process.destroy();
        }
    }
}