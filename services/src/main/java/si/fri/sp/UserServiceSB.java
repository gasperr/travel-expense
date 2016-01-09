package si.fri.sp;

import si.fri.sp.entities.User;
import si.fri.sp.interfaces.UserServiceLocal;

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
@Local(UserServiceLocal.class)
public class UserServiceSB implements UserServiceLocal {

    @PersistenceContext(unitName = "expense-persistence")
    EntityManager em;

    public void create(User ent) {
        if(ent != null){
            em.persist(ent);
        }else{
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public void update(User ent) {
        if(ent != null){
            em.merge(ent);
        }else{
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public void delete(User ent) {
        if(ent != null){
            em.remove(read(ent.getId()));
        }else{
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public User read(int entId) {
        User ue = em.find(User.class, entId);
        if(ue != null){
            return ue;
        }
        return null;
    }

    public List<User> readFromTo(int start, int end, boolean archived) {
        Query q = em.createNamedQuery("User.findAll");
        if (start != -1 && end != -1) {
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return (List<User>)q.getResultList();
    }
}
