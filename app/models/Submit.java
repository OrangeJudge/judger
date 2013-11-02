package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Date;

@Entity
public class Submit extends Model {
    @Id
    public long id;
    public int language;
    @Lob
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
        status = 1;
        createTime = new Date();
    }

    public static Finder<Long, Submit> find = new Finder<Long, Submit>(Long.class, Submit.class);
}
