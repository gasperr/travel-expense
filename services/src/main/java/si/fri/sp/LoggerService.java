package si.fri.sp;

import si.fri.sp.entities.Log;
import si.fri.sp.entities.enums.LogEnum;
import si.fri.sp.interfaces.LoggerLocal;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author Gasper Andrejc, created on 11/jan/2016
 */

@Stateless
@Local(LoggerLocal.class)
public class LoggerService implements LoggerLocal {
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
        LogEnum enumL = null;

        switch (type){
            case 0: enumL = LogEnum.USER_ZAHTEVEK;
                break;
            case 1: enumL = LogEnum.USER_NALOG;
                break;
            case 2: enumL = LogEnum.MANAGMENT_ZAHTEVEK;
                break;
            case 3: enumL = LogEnum.MANAGMENT_NALOG;
                break;
            case 4: enumL = LogEnum.FINANCE;
        }

        q.setParameter("type", enumL);
        return (List<Log>)q.getResultList();
    }
    public List<Log> findAll(int maxResults){
        Query q = em.createNamedQuery("Log.findAll");
        if(maxResults != -1){
            q.setMaxResults(maxResults);
        }
        return (List<Log>)q.getResultList();
    }
}
