package si.fri.sp.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Message.class)
public abstract class Message_ {

	public static volatile SingularAttribute<Message, String> content;
	public static volatile SingularAttribute<Message, Integer> id;
	public static volatile SingularAttribute<Message, Boolean> archived;
	public static volatile SingularAttribute<Message, User> fromUser;
	public static volatile SingularAttribute<Message, User> toUser;
	public static volatile SingularAttribute<Message, String> subject;
	public static volatile SingularAttribute<Message, Nalog> nalogRelated;
	public static volatile SingularAttribute<Message, Zahtevek> zahtevekRelated;
	public static volatile SingularAttribute<Message, Date> date;

}

