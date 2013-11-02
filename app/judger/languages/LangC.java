package judger.languages;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class LangC extends Language {
    private int timeLimit;
    private int memoryLimit;
    public LangC(int theTimeLimit, int theMemoryLimit) {
        timeLimit = theTimeLimit;
        memoryLimit = theMemoryLimit;
    }
    public void compile() {
        try {
            PrintWriter writer = new PrintWriter(new File("temp/compile.sh"));
            writer.println("cd exroot");
            writer.println("gcc -lm submit.c 2> compile_err.txt");
            writer.close();
            System.out.println("compile script created");
            Runtime r = Runtime.getRuntime();
            Process p = r.exec("chmod +x temp/compile.sh");
            p.waitFor();
            System.out.println("compile script make executable");
            p = r.exec("temp/compile.sh");
            p.waitFor();
            System.out.println("compiled");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void execute() {
        try {
            PrintWriter writer = new PrintWriter(new File("temp/run.sh"));
            writer.println("cd exroot");
            writer.println("chroot .");
            writer.println("./a.out < in.txt > out.txt 2> run_err.txt");
            writer.close();
            System.out.println("run script created");
            Runtime r = Runtime.getRuntime();
            Process p = r.exec("chmod +x temp/run.sh");
            p.waitFor();
            System.out.println("run script make executable");
            p = r.exec("temp/run.sh");
            p.waitFor();
            System.out.println("ran");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
