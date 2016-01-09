package si.fri.sp.entities;

import si.fri.sp.entities.generic.BasicResource;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
@Entity
@NamedQueries({@NamedQuery(name="Message.findAll", query="SELECT o FROM MessageEntity o where o.archived = :archived"),
        @NamedQuery(name="Message.getUserOutgoing", query = "SELECT m FROM MessageEntity m JOIN m.fromUser u where u.id = :uid and m.archived = :archived"),
        @NamedQuery(name="Message.getUserIncoming", query = "SELECT m FROM MessageEntity m JOIN m.toUser u where u.id = :uid and m.archived = :archived"),
        @NamedQuery(name="Message.getByZahtevek", query = "SELECT m FROM MessageEntity m JOIN m.zahtevekRelated u where u.id = :uid and m.archived = :archived"),
        @NamedQuery(name="Message.getByNalog", query = "SELECT m FROM MessageEntity m JOIN m.nalogRelated u where u.id = :uid and m.archived = :archived")
})
@Table(name = "message")
public class MessageEntity extends BasicResource implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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



    public MessageEntity() {
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
