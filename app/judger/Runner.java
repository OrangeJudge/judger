package judger;

import judger.languages.LangC;
import models.Submit;
import org.h2.store.fs.FileUtils;
import utils.OJException;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.Scanner;

public class Runner implements Runnable {
    public Submit submit;
    public boolean specialJudge;
    public int tests;
    public String[] testDetail;

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
        OJException oje = null;
        readProblem();
        submit.status = 102;
        try {
            compile();
        } catch (OJException e) {
            oje = e;
        }
        if (oje != null) {
            submit.status = oje.getCode();
            submit.detail = oje.getMessage();
            submit.finishTime = new Date();
            submit.save();
            return;
        } else {
            submit.status = 103;
            submit.finishTime = new Date();
            submit.save();
        }

        for (int i = 0; i < this.tests; i++) {
            try {
                execute(i);
            } catch (OJException e) {
                oje = e;
            }
            if (oje != null) {
                submit.status = oje.getCode();
                submit.detail = oje.getMessage();
                submit.finishTime = new Date();
                submit.save();
                return;
            }
        }

        submit.status = 200;
        submit.finishTime = new Date();
        submit.save();
    }

    public void readProblem() {
        try {
            Scanner scanner = new Scanner(new File("problems/" + submit.problemId + "/problem.txt"));
            long problemTimeStamp = scanner.nextLong();
            int specialJudge = scanner.nextInt();
            int tests = scanner.nextInt();
            this.specialJudge = specialJudge == 1;
            this.tests = tests;
            this.testDetail = new String[tests];
            scanner.nextLine();
            for (int i = 0; i < tests; i++) {
                this.testDetail[i] = scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void compile() throws OJException {
        try {
            FileUtils.deleteRecursive("temp", true);
            (new File("temp/exroot")).mkdirs();
            switch (submit.language) {
                case 0: {
                    PrintWriter writer = new PrintWriter(new File("temp/exroot/submit.c"));
                    writer.print(submit.source);
                    writer.close();
                    LangC langC = new LangC();
                    langC.compile();
                    File compiled = new File("temp/exroot/a.out");
                    if (compiled.exists()) {
                        return;
                    } else {
                        File compileErrorFile = new File("temp/exroot/compile_err.txt");
                        FileInputStream fis = new FileInputStream(compileErrorFile);
                        byte[] data = new byte[(int)compileErrorFile.length()];
                        fis.read(data);
                        fis.close();
                        String error = new String(data, "UTF-8");
                        throw new OJException(200, error);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new OJException(200);
    }

    public void execute(int i) throws OJException {
        System.out.println("execute " + i);
        try {
            String[] testCase = this.testDetail[i].split("\\|");
            File in = new File("problems/" + submit.problemId + "/files/" + testCase[0]);
            File out = new File("problems/" + submit.problemId + "/files/" + testCase[1]);
            File inTest = new File("temp/exroot/in.txt");
            copyFile(in, inTest);
            LangC langC = new LangC();
            langC.execute(Integer.parseInt(testCase[2]), Integer.parseInt(testCase[3]));
            if (!compareFile(out, new File("temp/exroot/out.txt"))) {
                throw new OJException(304, "Wrong Answer on Test " + i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if(!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        }
        finally {
            if(source != null) {
                source.close();
            }
            if(destination != null) {
                destination.close();
            }
        }
    }

    public static boolean compareFile(File file1, File file2) {
        String str1 = null, str2 = null;
        try {
            str1 = readFromFile(file1);
            str2 = readFromFile(file2);
        } catch (IOException e) {
        }
        return str1.equals(str2);
    }

    public static String readFromFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int)file.length()];
        fis.read(data);
        fis.close();
        return new String(data, "UTF-8");
    }
}
