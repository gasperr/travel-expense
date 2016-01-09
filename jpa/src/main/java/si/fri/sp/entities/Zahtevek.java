package si.fri.sp.entities;

import si.fri.sp.entities.enums.ZahtevekStatus;
import si.fri.sp.entities.generic.BasicResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
@Entity
@NamedQueries({@NamedQuery(name="Zahtevek.findAll", query="SELECT o FROM Zahtevek o where o.archived = :archived"),
        @NamedQuery(name= "Zahtevek.byReviewedBy", query = "SELECT z FROM Zahtevek z JOIN z.reviewedBy u where u.id = :uid and z.archived = :archived"),
        @NamedQuery(name= "Zahtevek.byUserId", query = "SELECT z FROM Zahtevek z JOIN z.owner u where u.id = :uid and z.archived = :archived"),
        @NamedQuery(name="Zahtevek.findByNalog", query = "SELECT z FROM Zahtevek z JOIN z.nalog n where n.id = :nid and z.archived = :archived")
})
@Table(name = "zahtevek")
public class Zahtevek extends BasicResource implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(length=50)
    private String location;

    private Date fromDate;

    private Date toDate;

    private Double costs;

    @Column(length=1024)
    private String content;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private ZahtevekStatus status;

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy;

    @ManyToOne
    @JoinColumn(name = "owned_by")
    private User owner;

    @OneToMany(mappedBy = "zahtevekRelated", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<MessageEntity> relatedMessage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nalog")
    private Nalog nalog;

    private boolean archived;

    public Zahtevek() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date from) {
        this.fromDate = from;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date to) {
        this.toDate = to;
    }

    public Double getCosts() {
        return costs;
    }

    public void setCosts(Double costs) {
        this.costs = costs;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ZahtevekStatus getStatus() {
        return status;
    }

    public void setStatus(ZahtevekStatus status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(User reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Nalog getNalog() {
        return nalog;
    }

    public void setNalog(Nalog nalog) {
        this.nalog = nalog;
    }

    public List<MessageEntity> getRelatedMessage() {
        if(relatedMessage == null){
            relatedMessage = new ArrayList<MessageEntity>();
        }
        return relatedMessage;
    }


}
