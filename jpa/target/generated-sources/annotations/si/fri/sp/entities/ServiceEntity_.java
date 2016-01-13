package si.fri.sp.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ServiceEntity.class)
public abstract class ServiceEntity_ {

	public static volatile SingularAttribute<ServiceEntity, Integer> id;
	public static volatile SingularAttribute<ServiceEntity, Double> price;
	public static volatile SingularAttribute<ServiceEntity, Double> approvedPrice;
	public static volatile SingularAttribute<ServiceEntity, Nalog> nalog;
	public static volatile SingularAttribute<ServiceEntity, String> notes;
	public static volatile SingularAttribute<ServiceEntity, String> type;

}

