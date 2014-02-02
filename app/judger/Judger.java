package judger;

public class Judger {
    public static void judge(Submit submit) {
        (new Thread(new Runner(submit))).start();
    }
}
