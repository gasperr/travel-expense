package si.fri.sp;

import si.fri.sp.entities.Nalog;
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

    public void create(Nalog ent) {
        if (ent != null) {
            em.persist(ent);
        } else {
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public void update(Nalog ent) {
        if (ent != null) {
            em.merge(ent);
        } else {
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public void delete(Nalog ent) {
        if (ent != null) {
            em.remove(read(ent.getId()));
        } else {
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public Nalog read(int entId) {
        Nalog ue = em.find(Nalog.class, entId);
        if (ue != null) {
            return ue;
        }
        return null;
    }

    public List<Nalog> readFromTo(int start, int end, boolean archived) {
        Query q = em.createNamedQuery("Nalog.findAll");
        q.setParameter("archived", archived);

        if (start != -1 && end != -1) {
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return (List<Nalog>) q.getResultList();
    }

    public List<Nalog> getAllByApprovedUserId(int uid, boolean archived) {
        Query q = em.createNamedQuery("Nalog.byApprovedUserId");
        q.setParameter("archived", archived);
        q.setParameter("uid", uid);

        return (List<Nalog>) q.getResultList();
    }

    public List<Nalog> getAllByUserId(int uid, boolean archived) {
        Query q = em.createNamedQuery("Nalog.byUserId");
        q.setParameter("archived", archived);
        q.setParameter("uid", uid);

        return (List<Nalog>) q.getResultList();
    }

    public Nalog findByZahtevek(int zid, boolean archived) {
        Query q = em.createNamedQuery("Nalog.findByZahtevek");
        q.setParameter("uid", zid);
        q.setParameter("archived", archived);

        return (Nalog)q.getSingleResult();
    }
}
