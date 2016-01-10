package si.fri.sp.entities;

import si.fri.sp.entities.enums.NalogStatus;
import si.fri.sp.entities.generic.BasicResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
@Entity
@NamedQueries({@NamedQuery(name="Nalog.findAll", query="SELECT o FROM Nalog o where o.archived = :archived"),
        @NamedQuery(name= "Nalog.byApprovedUserId", query = "SELECT z FROM Nalog z JOIN z.approvedBy u where u.id = :uid and z.archived = :archived"),
        @NamedQuery(name= "Nalog.byUserId", query = "SELECT z FROM Nalog z JOIN z.owner u where u.id = :uid and z.archived = :archived"),
        @NamedQuery(name="Nalog.findByZahtevek", query = "SELECT z FROM Nalog z JOIN z.zahtevek u where u.id = :uid and z.archived = :archived")
})
@Table(name = "nalog")
@XmlRootElement
public class Nalog extends BasicResource implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @XmlID
    @XmlElement
    private int id;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private NalogStatus status;

    @Column(length = 50)
    private String location;

    @Column(length = 1024)
    private String purpose;

    private Date fromDate;

    private Date toDate;

    @Column(name = "executed_on")
    private Date executedOn;

    @Column(length = 1024)
    private String notes;

    @Column(length = 2048)
    private String content;

    private double covered;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @ManyToOne
    @JoinColumn(name = "owned_by")
    private User owner;

    @OneToMany(mappedBy = "nalogRelated", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Message> relatedMessage;

    @OneToMany(mappedBy = "nalog", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<ServiceEntity> services;

    @OneToOne(mappedBy = "nalog", fetch = FetchType.LAZY)
    private Zahtevek zahtevek;

    private boolean archived;


    public Nalog() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCovered() {
        return covered;
    }

    public Date getExecutedOn() {
        return executedOn;
    }

    public void setExecutedOn(Date executedOn) {
        this.executedOn = executedOn;
    }

    public void setCovered(double covered) {
        this.covered = covered;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public NalogStatus getStatus() {
        return status;
    }

    public void setStatus(NalogStatus status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Zahtevek getZahtevek() {
        return zahtevek;
    }

    public void setZahtevek(Zahtevek zahtevekEntity) {
        this.zahtevek = zahtevekEntity;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Message> getRelatedMessage() {
        if(relatedMessage == null){
            relatedMessage = new HashSet<>();
        }
        List<Message> rtrn = new ArrayList<>();
        rtrn.addAll(relatedMessage);
        return rtrn;
    }
    public List<ServiceEntity> getServices() {
        if(services == null){
            services = new HashSet<>();
        }
        List<ServiceEntity> rtrn = new ArrayList<>();
        rtrn.addAll(services);
        return rtrn;
    }

}
