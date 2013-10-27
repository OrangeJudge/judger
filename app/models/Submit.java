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
    @Lob
    public String source;

    public int problemId;
    public long problemTimeStamp;

    public Date createTime;

    public int status;
    public String detail;

    public Submit() {
        status = 0;
        createTime = new Date();
    }
}
