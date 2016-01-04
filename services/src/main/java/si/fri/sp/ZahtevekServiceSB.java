package si.fri.sp;

import si.fri.sp.entities.ZahtevekEntity;
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

    public void create(ZahtevekEntity ent) {
        if (ent != null) {
            em.persist(ent);
        } else {
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public void update(ZahtevekEntity ent) {
        if (ent != null) {
            em.merge(ent);
        } else {
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public void delete(ZahtevekEntity ent) {
        if (ent != null) {
            em.remove(read(ent.getId()));
        } else {
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public ZahtevekEntity read(int entId) {
        ZahtevekEntity ue = em.find(ZahtevekEntity.class, entId);
        if (ue != null) {
            return ue;
        }
        return null;
    }

    public List<ZahtevekEntity> readFromTo(int start, int end) {
        Query q = em.createNamedQuery("Zahtevek.findAll");
        if (start != -1 && end != -1) {
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return (List<ZahtevekEntity>) q.getResultList();
    }

    public List<ZahtevekEntity> getAllByUserId(int uid) {
        Query q = em.createNamedQuery("Zahtevek.byUserId");
        q.setParameter("uid", uid);
        return (List<ZahtevekEntity>) q.getResultList();
    }
}
