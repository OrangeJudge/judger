package judger;

import judger.languages.LangC;
import models.Submit;
import org.h2.store.fs.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Runner implements Runnable {
    public Submit submit;

    public Runner(Submit submit) {
        this.submit = submit;
    }

    public void run() {
        System.out.println("run " + submit.id);
        System.out.println(submit.source);
        System.out.println("ran " + submit.id);
        judge();
        Judger.running = false;
        Judger.start();
    }

    public void judge() {
        compile();
        submit.status = 100;
        submit.finishTime = new Date();
        submit.save();
    }

    public boolean compile() {
        try {
            switch (submit.language) {
                case 0: {
                    FileUtils.deleteRecursive("temp", true);
                    (new File("temp/exroot")).mkdirs();
                    PrintWriter writer = new PrintWriter(new File("temp/exroot/submit.c"));
                    writer.print(submit.source);
                    writer.close();
                    LangC langC = new LangC();
                    langC.compile();
                    File compiled = new File("temp/exroot/a.out");
                    if (compiled.exists()) {
                        return true;
                    } else {
                        File compileErrorFile = new File("temp/exroot/compile_err.txt");
                        FileInputStream fis = new FileInputStream(compileErrorFile);
                        byte[] data = new byte[(int)compileErrorFile.length()];
                        fis.read(data);
                        fis.close();
                        String error = new String(data, "UTF-8");
                        submit.detail = error;
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
