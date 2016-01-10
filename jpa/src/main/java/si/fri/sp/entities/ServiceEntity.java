package si.fri.sp.entities;

import si.fri.sp.entities.generic.BasicResource;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
@Entity
@NamedQueries({@NamedQuery(name="Service.findAll", query="SELECT o FROM ServiceEntity o")
})
@Table(name = "service")
@XmlRootElement
public class ServiceEntity extends BasicResource implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @XmlID
    @XmlElement
    private int id;

    @Column(length = 50)
    private String type;

    @Column(length = 1024)
    private String notes;

    private Double price;

    @Column(name = "approved_price")
    private Double approvedPrice;

    @ManyToOne
    @JoinColumn(name = "nalog")
    private Nalog nalog;

    @Transient
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ServiceEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getApprovedPrice() {
        if(approvedPrice == null){
            return price;
        }
        return approvedPrice;
    }

    public void setApprovedPrice(Double approvedPrice) {
        this.approvedPrice = approvedPrice;
    }

    public Nalog getNalog() {
        return nalog;
    }

    public void setNalog(Nalog nalog) {
        this.nalog = nalog;
    }

    @Override
    public String toString() {
        return type +"; "+ notes+"; approved price: "+getApprovedPrice()+"; reported price: "+price;
    }
}
