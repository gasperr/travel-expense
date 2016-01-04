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
@NamedQueries({@NamedQuery(name="Zahtevek.findAll", query="SELECT o FROM ZahtevekEntity o"),
        @NamedQuery(name="Zahtevek.byUserId", query = "SELECT z FROM ZahtevekEntity z JOIN z.reviewedBy u where u.id = :uid")
})
@Table(name = "zahtevek")
public class ZahtevekEntity extends BasicResource implements Serializable {
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
    private UserEntity reviewedBy;

    @OneToMany(mappedBy = "zahtevekRelated", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<MessageEntity> relatedMessage;

    public ZahtevekEntity() {
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

    public UserEntity getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(UserEntity reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public List<MessageEntity> getRelatedMessage() {
        if(relatedMessage == null){
            relatedMessage = new ArrayList<MessageEntity>();
        }
        return relatedMessage;
    }


}
