package si.fri.sp.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import si.fri.sp.entities.enums.ZahtevekStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Zahtevek.class)
public abstract class Zahtevek_ {

	public static volatile SingularAttribute<Zahtevek, String> location;
	public static volatile SingularAttribute<Zahtevek, ZahtevekStatus> status;
	public static volatile SingularAttribute<Zahtevek, Date> toDate;
	public static volatile SingularAttribute<Zahtevek, String> noteByManagment;
	public static volatile SingularAttribute<Zahtevek, Double> costs;
	public static volatile SingularAttribute<Zahtevek, User> reviewedBy;
	public static volatile SingularAttribute<Zahtevek, Integer> id;
	public static volatile SingularAttribute<Zahtevek, String> content;
	public static volatile SingularAttribute<Zahtevek, Boolean> archived;
	public static volatile SingularAttribute<Zahtevek, Date> fromDate;
	public static volatile SingularAttribute<Zahtevek, Nalog> nalog;
	public static volatile SingularAttribute<Zahtevek, User> owner;
	public static volatile SetAttribute<Zahtevek, Message> relatedMessage;

}

