package judger;

import java.util.Date;

public class Submit {
    public long id;
    public String server; // the address that post result to.

    public int language;
    public String source;

    public int problemId;
    public long problemTimeStamp;
    // problem time stamp is to ensure the problem data of judger is up to date.

    public Date createTime;
    public Date finishTime;

    public int status;
    public String detail;
    // detail includes possible judging outputs.

    public Submit() {
        status = 100;
        createTime = new Date();
    }
}
