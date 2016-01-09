package si.fri.sp;

import si.fri.sp.entities.Zahtevek;
import si.fri.sp.interfaces.ZahtevekServiceLocal;

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
@Local(ZahtevekServiceLocal.class)
public class ZahtevekServiceSB implements ZahtevekServiceLocal {
    @PersistenceContext(unitName = "expense-persistence")
    EntityManager em;

    public void create(Zahtevek ent) {
        if (ent != null) {
            em.persist(ent);
        } else {
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public void update(Zahtevek ent) {
        if (ent != null) {
            em.merge(ent);
        } else {
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public void delete(Zahtevek ent) {
        if (ent != null) {
            em.remove(read(ent.getId()));
        } else {
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public Zahtevek read(int entId) {
        Zahtevek ue = em.find(Zahtevek.class, entId);
        if (ue != null) {
            return ue;
        }
        return null;
    }

    public List<Zahtevek> readFromTo(int start, int end, boolean archived) {
        Query q = em.createNamedQuery("Zahtevek.findAll");
        q.setParameter("archived", archived);
        if (start != -1 && end != -1) {
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return (List<Zahtevek>) q.getResultList();
    }

    public List<Zahtevek> getAllByReviewedBy(int uid, boolean archived) {
        Query q = em.createNamedQuery("Zahtevek.byReviewedBy");
        q.setParameter("archived", archived);
        q.setParameter("uid", uid);
        return (List<Zahtevek>) q.getResultList();
    }

    public Zahtevek findByNalog(int nid, boolean archived){
        Query q = em.createNamedQuery("Zahtevek.findByNalog");
        q.setParameter("archived", archived);
        q.setParameter("nid", nid);
        return (Zahtevek)q.getSingleResult();
    }

    public List<Zahtevek> getAllByUserId(int uid, boolean archived) {
        Query q = em.createNamedQuery("Zahtevek.byUserId");
        q.setParameter("archived", archived);
        q.setParameter("uid", uid);
        return (List<Zahtevek>) q.getResultList();
    }
}
