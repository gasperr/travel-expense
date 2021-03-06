package si.fri.sp.entities;

import si.fri.sp.entities.generic.BasicResource;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
@Entity
@NamedQueries({@NamedQuery(name="Message.findAll", query="SELECT o FROM Message o where o.archived = :archived"),
        @NamedQuery(name="Message.getUserOutgoing", query = "SELECT m FROM Message m JOIN m.fromUser u where u.id = :uid and m.archived = :archived"),
        @NamedQuery(name="Message.getUserIncoming", query = "SELECT m FROM Message m JOIN m.toUser u where u.id = :uid and m.archived = :archived"),
        @NamedQuery(name="Message.getByZahtevek", query = "SELECT m FROM Message m JOIN m.zahtevekRelated u where u.id = :uid and m.archived = :archived"),
        @NamedQuery(name="Message.getByNalog", query = "SELECT m FROM Message m JOIN m.nalogRelated u where u.id = :uid and m.archived = :archived")
})
@Table(name = "message")
@XmlRootElement
public class Message extends BasicResource implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlID
    @XmlElement
    private int id;

    @Column(name = "content", length = 2048)
    private String content;

    @ManyToOne
    @JoinColumn(name = "nalog_related")
    private Nalog nalogRelated;

    @ManyToOne
    @JoinColumn(name = "zahtevek_related")
    private Zahtevek zahtevekRelated;

    @ManyToOne
    @JoinColumn(name = "to_user")
    private User toUser;

    @ManyToOne
    @JoinColumn(name = "from_user")
    private User fromUser;

    private boolean archived;

    private Date date;

    private String subject;



    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Nalog getNalogRelated() {
        return nalogRelated;
    }

    public void setNalogRelated(Nalog nalogRelated) {
        this.nalogRelated = nalogRelated;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Zahtevek getZahtevekRelated() {
        return zahtevekRelated;
    }

    public void setZahtevekRelated(Zahtevek zahtevekRelated) {
        this.zahtevekRelated = zahtevekRelated;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }
}
