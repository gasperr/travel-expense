package si.fri.sp.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import si.fri.sp.entities.enums.UserType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SetAttribute<User, Zahtevek> zahtevki;
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SetAttribute<User, Zahtevek> reviewedZahtevki;
	public static volatile SetAttribute<User, Message> messagesOutgoing;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SetAttribute<User, Nalog> nalogi;
	public static volatile SingularAttribute<User, String> surname;
	public static volatile SingularAttribute<User, UserType> type;
	public static volatile SetAttribute<User, Message> messagesIncoming;

}

