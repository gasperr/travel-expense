package si.fri.sp.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import si.fri.sp.entities.enums.NalogStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Nalog.class)
public abstract class Nalog_ {

	public static volatile SingularAttribute<Nalog, Date> executedOn;
	public static volatile SetAttribute<Nalog, ServiceEntity> services;
	public static volatile SingularAttribute<Nalog, NalogStatus> status;
	public static volatile SingularAttribute<Nalog, String> location;
	public static volatile SingularAttribute<Nalog, String> purpose;
	public static volatile SingularAttribute<Nalog, Date> toDate;
	public static volatile SingularAttribute<Nalog, User> approvedBy;
	public static volatile SingularAttribute<Nalog, Integer> id;
	public static volatile SingularAttribute<Nalog, String> content;
	public static volatile SingularAttribute<Nalog, Boolean> archived;
	public static volatile SingularAttribute<Nalog, Date> fromDate;
	public static volatile SingularAttribute<Nalog, Zahtevek> zahtevek;
	public static volatile SetAttribute<Nalog, Message> relatedMessage;
	public static volatile SingularAttribute<Nalog, User> owner;
	public static volatile SingularAttribute<Nalog, String> notes;
	public static volatile SingularAttribute<Nalog, Double> covered;

}

