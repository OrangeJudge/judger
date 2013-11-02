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
        compile();
        submit.status = 2;
        submit.save();
        System.out.println("ran " + submit.id);
        Judger.running = false;
        Judger.start();
    }

    public void compile() {
        switch (submit.language) {
            case 0: {
                try {
                    FileUtils.deleteRecursive("temp", true);
                    (new File("temp/exroot")).mkdirs();
                    PrintWriter writer = new PrintWriter(new File("temp/exroot/submit.c"));
                    writer.print(submit.source);
                    writer.close();
                    LangC langC = new LangC();
                    // langC.compile();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
