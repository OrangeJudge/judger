package judger;

import judger.languages.LangC;
import models.Submit;
import org.h2.store.fs.FileUtils;
import utils.OJException;

import java.io.*;
import java.util.Date;

public class Runner implements Runnable {
    public Submit submit;

    public Runner(Submit submit) {
        this.submit = submit;
    }

    public void run() {
        System.out.println("run " + submit.id);
        System.out.println(submit.source);
        /*
        try {
            compile();
            submit.status = 100;
        } catch (OJException ex) {
            submit.status = ex.getCode();
            submit.detail = ex.getMessage();
        }
        */
        test();
        submit.finishTime = new Date();
        submit.status = 100;
        submit.save();
        System.out.println("ran " + submit.id);
        Judger.running = false;
        Judger.start();
    }

    public void test() {
        FileUtils.deleteRecursive("temp", true);
        (new File("temp/exroot")).mkdirs();
        /*
        try {
            PrintWriter writer = new PrintWriter(new File("temp/exroot/submit.c"));
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        */
    }

    public void compileSubmit() throws OJException {
        String error = null;
        switch (submit.language) {
            case 0: {
                try {
                    FileUtils.deleteRecursive("temp", true);
                    (new File("temp/exroot")).mkdirs();
                    PrintWriter writer = new PrintWriter(new File("temp/exroot/submit.c"));
                    writer.print(submit.source);
                    writer.close();
                    LangC langC = new LangC();
                    langC.compile();
                    File compiled = new File("temp/exroot/a.out");
                    if (compiled.exists()) {
                        return;
                    } else {
                        File compileErrorFile = new File("/temp/exroot/compile_err.txt");
                        FileInputStream fis = new FileInputStream(compileErrorFile);
                        byte[] data = new byte[(int)compileErrorFile.length()];
                        fis.read(data);
                        fis.close();
                        error = new String(data, "UTF-8");
                    }
                } catch (FileNotFoundException e) {
                    System.out.print("File not found");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        throw new OJException(200, error);
    }
}
