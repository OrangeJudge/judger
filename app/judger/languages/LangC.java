package judger.languages;

import judger.Timer;
import utils.OJException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class LangC extends Language {
    public boolean timeLimitExceeded;
    public void compile() throws OJException {
        try {
            PrintWriter writer = new PrintWriter(new File("temp/compile.sh"));
            writer.println("cd temp/exroot");
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
            File compiled = new File("temp/exroot/a.out");
            if (!compiled.exists()) {
                File compileErrorFile = new File("temp/exroot/compile_err.txt");
                FileInputStream fis = new FileInputStream(compileErrorFile);
                byte[] data = new byte[(int)compileErrorFile.length()];
                fis.read(data);
                fis.close();
                String error = new String(data, "UTF-8");
                throw new OJException(300, error);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void execute(int timeLimit, int memoryLimit) throws OJException {
        try {
            PrintWriter writer = new PrintWriter(new File("temp/run.sh"));
            writer.println("cd temp/exroot");
            writer.println("chroot .");
            writer.println("ulimit -d 8192 -v 0 -m " + memoryLimit*1024);
            writer.println("./a.out < in.txt > out.txt 2> run_err.txt");
            writer.close();
            System.out.println("run script created");
            Runtime r = Runtime.getRuntime();
            Process process = r.exec("chmod +x temp/run.sh");
            process.waitFor();
            System.out.println("run script make executable");
            process = r.exec("temp/run.sh");
            (new Timer(this, process, timeLimit)).start();
            process.waitFor();
            if (timeLimitExceeded) {
                throw new OJException(302);
            }
            System.out.println("ran");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
