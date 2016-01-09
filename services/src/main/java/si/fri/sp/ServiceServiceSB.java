package si.fri.sp;

import si.fri.sp.entities.ServiceEntity;
import si.fri.sp.interfaces.ServiceServiceLocal;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */

@Stateless
@Local(ServiceServiceLocal.class)
public class ServiceServiceSB implements ServiceServiceLocal {
    @PersistenceContext(unitName = "expense-persistence")
    EntityManager em;

    public void create(ServiceEntity ent) {
        if (ent != null) {
            em.persist(ent);
        } else {
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public void update(ServiceEntity ent) {
        if (ent != null) {
            em.merge(ent);
        } else {
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public void delete(ServiceEntity ent) {
        if (ent != null) {
            em.remove(read(ent.getId()));
        } else {
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public ServiceEntity read(int entId) {
        ServiceEntity ue = em.find(ServiceEntity.class, entId);
        if (ue != null) {
            return ue;
        }
        return null;
    }

    public List<ServiceEntity> readFromTo(int start, int end, boolean archived) {
        Query q = em.createNamedQuery("Service.findAll");
        q.setParameter("archived", archived);
        if (start != -1 && end != -1) {
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return (List<ServiceEntity>) q.getResultList();
    }
}
