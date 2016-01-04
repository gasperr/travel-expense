package si.fri.sp;

import si.fri.sp.entities.NalogEntity;
import si.fri.sp.interfaces.NalogServiceLocal;

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
@Local(NalogServiceLocal.class)
public class NalogServiceSB implements NalogServiceLocal {

    @PersistenceContext(unitName = "expense-persistence")
    EntityManager em;

    public void create(NalogEntity ent) {
        if (ent != null) {
            em.persist(ent);
        } else {
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public void update(NalogEntity ent) {
        if (ent != null) {
            em.merge(ent);
        } else {
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public void delete(NalogEntity ent) {
        if (ent != null) {
            em.remove(read(ent.getId()));
        } else {
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public NalogEntity read(int entId) {
        NalogEntity ue = em.find(NalogEntity.class, entId);
        if (ue != null) {
            return ue;
        }
        return null;
    }

    public List<NalogEntity> readFromTo(int start, int end) {
        Query q = em.createNamedQuery("Nalog.findAll");
        if (start != -1 && end != -1) {
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return (List<NalogEntity>) q.getResultList();
    }

    public List<NalogEntity> getAllByUserId(int uid) {
        Query q = em.createNamedQuery("Nalog.byUserId");
        q.setParameter("uid", uid);
        return (List<NalogEntity>) q.getResultList();
    }
}
