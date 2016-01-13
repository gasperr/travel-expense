package si.fri.sp.entities;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import si.fri.sp.entities.enums.LogEnum;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Log.class)
public abstract class Log_ {

	public static volatile SingularAttribute<Log, Integer> id;
	public static volatile SingularAttribute<Log, LogEnum> type;
	public static volatile SingularAttribute<Log, Timestamp> date;
	public static volatile SingularAttribute<Log, String> log;

}

