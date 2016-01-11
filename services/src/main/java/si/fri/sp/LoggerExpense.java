package si.fri.sp;

import si.fri.sp.entities.Log;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author Gasper Andrejc, created on 11/jan/2016
 */
public class LoggerExpense {
    @PersistenceContext(unitName = "expense-persistence")
    EntityManager em;

    public void create(Log ent) {
        if(ent != null){
            em.persist(ent);
        }else{
            throw new IllegalArgumentException("Passed entity is null");
        }
    }
    public List<Log> findInbetween(Timestamp start, Timestamp end) {
        Query q = em.createNamedQuery("Log.dateInbetween");
        q.setParameter("dateStart", start);
        q.setParameter("dateEnd", end);
        return (List<Log>)q.getResultList();
    }

    public List<Log> findByType(int type){
        Query q = em.createNamedQuery("Log.findByType");
        q.setParameter("type", type);
        return (List<Log>)q.getResultList();
    }
    public List<Log> findAll(int maxResults){
        Query q = em.createNamedQuery("Log.findAll");
        q.setMaxResults(maxResults);
        return (List<Log>)q.getResultList();
    }
}
