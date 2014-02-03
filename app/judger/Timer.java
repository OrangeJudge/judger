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
            System.out.println("[Timer] sleep " + timeLimit);
            sleep(timeLimit);
            System.out.println("[Timer] time out");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            process.exitValue();
            language.timeLimitExceeded = false;
        } catch (IllegalThreadStateException e) {
            language.timeLimitExceeded = true;
            System.out.println("[Timer] try to destroy process.");
            process.destroy();
            System.out.println("[Timer] destroyed process.");
        }
    }
}