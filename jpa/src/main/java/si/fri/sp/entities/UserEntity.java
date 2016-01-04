package si.fri.sp.entities;

import si.fri.sp.entities.enums.UserType;
import si.fri.sp.entities.generic.BasicResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
@Entity
@NamedQueries({@NamedQuery(name="User.findAll", query="SELECT o FROM UserEntity o")
})
@Table(name = "user")
public class UserEntity extends BasicResource implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String name;


    @Column(length = 50)
    private String surname;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(length=5)
    private UserType type;

    @OneToMany(mappedBy = "reviewedBy", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    List<ZahtevekEntity> zahtevki;

    @OneToMany(mappedBy = "toUser", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    List<MessageEntity> messagesIncoming;

    @OneToMany(mappedBy = "fromUser", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    List<MessageEntity> messagesOutgoing;

    public UserEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public List<ZahtevekEntity> getZahtevki() {
        if(this.zahtevki == null){
            this.zahtevki = new ArrayList<ZahtevekEntity>();
        }
        return this.zahtevki;
    }

    public List<MessageEntity> getMessagesIncoming() {
        if(this.messagesIncoming == null){
            this.messagesIncoming = new ArrayList<MessageEntity>();
        }
        return messagesIncoming;
    }

    public List<MessageEntity> getMessagesOutgoing() {
        if(this.messagesOutgoing == null){
            this.messagesOutgoing = new ArrayList<MessageEntity>();
        }
        return messagesOutgoing;
    }
}
