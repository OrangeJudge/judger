package judger;

import judger.languages.LangC;
import models.Submit;
import org.h2.store.fs.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Runner extends Thread {
    public Submit submit;

    public Runner(Submit submit) {
        this.submit = submit;
    }

    public void run() {
        System.out.println("run " + submit.id);
        compile();
        submit.status = 2;
        submit.save();
        System.out.println("ran " + submit.id);
        Judger.running = false;
        Judger.start();
    }

    public void compile() {
        System.out.println("compile " + submit.id);
        System.out.println(submit.source);
        String source = submit.source;
        // Fatal error would happen if we directly use submit.source.
        // Anyone got any idea?
        switch (submit.language) {
            case 0: {
                try {
                    FileUtils.deleteRecursive("temp", true);
                    (new File("temp/exroot")).mkdirs();
                    PrintWriter writer = new PrintWriter(new File("temp/exroot/submit.c"));
                    writer.print(source);
                    writer.close();
                    LangC langC = new LangC();
                    langC.compile();
                } catch (FileNotFoundException e) {
                    System.out.print("File not found");
                } catch (Exception e) {
                    System.out.print("Unknown exception");
                }
                break;
            }
        }
    }
}
