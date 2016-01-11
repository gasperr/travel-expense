package si.fri.sp.entities;

import si.fri.sp.entities.enums.LogEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author Gasper Andrejc, created on 11/jan/2016
 */

@NamedQueries({@NamedQuery(name = "Log.findAll", query = "SELECT l FROM Log l"),
        @NamedQuery(name = "Log.findByType", query = "SELECT l FROM Log l WHERE l.type = :type"),
        @NamedQuery(name = "Log.dateInbetween", query = "SELECT l FROM Log l WHERE l.date > :dateStart and l.date < :dateEnd")})

@Entity
@Table(name = "log")
public class Log implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    Timestamp date;

    String log;

    LogEnum type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public LogEnum getType() {
        return type;
    }

    public void setType(LogEnum type) {
        this.type = type;
    }
}
