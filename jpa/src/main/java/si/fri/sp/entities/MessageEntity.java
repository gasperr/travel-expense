package si.fri.sp.entities;

import si.fri.sp.entities.generic.BasicResource;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
@Entity
@NamedQueries({@NamedQuery(name="Message.findAll", query="SELECT o FROM MessageEntity o"),
        @NamedQuery(name="Message.getUserOutgoing", query = "SELECT m FROM MessageEntity m JOIN m.fromUser u where u.id = :uid"),
        @NamedQuery(name="Message.getUserIncoming", query = "SELECT m FROM MessageEntity m JOIN m.toUser u where u.id = :uid"),
        @NamedQuery(name="Message.getByZahtevek", query = "SELECT m FROM MessageEntity m JOIN m.zahtevekRelated u where u.id = :uid"),
        @NamedQuery(name="Message.getByNalog", query = "SELECT m FROM MessageEntity m JOIN m.nalogRelated u where u.id = :uid")
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
    private NalogEntity nalogRelated;

    @ManyToOne
    @JoinColumn(name = "zahtevek_related")
    private ZahtevekEntity zahtevekRelated;

    @ManyToOne
    @JoinColumn(name = "to_user")
    private UserEntity toUser;

    @ManyToOne
    @JoinColumn(name = "from_user")
    private UserEntity fromUser;



    public MessageEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public NalogEntity getNalogRelated() {
        return nalogRelated;
    }

    public void setNalogRelated(NalogEntity nalogRelated) {
        this.nalogRelated = nalogRelated;
    }


    public ZahtevekEntity getZahtevekRelated() {
        return zahtevekRelated;
    }

    public void setZahtevekRelated(ZahtevekEntity zahtevekRelated) {
        this.zahtevekRelated = zahtevekRelated;
    }

    public UserEntity getToUser() {
        return toUser;
    }

    public void setToUser(UserEntity toUser) {
        this.toUser = toUser;
    }

    public UserEntity getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserEntity fromUser) {
        this.fromUser = fromUser;
    }
}
