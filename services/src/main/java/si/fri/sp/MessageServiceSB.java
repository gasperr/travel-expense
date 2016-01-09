package si.fri.sp;

import si.fri.sp.entities.Message;
import si.fri.sp.interfaces.MessageServiceLocal;

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
@Local(MessageServiceLocal.class)
public class MessageServiceSB implements MessageServiceLocal {

    @PersistenceContext(unitName = "expense-persistence")
    EntityManager em;

    public void create(Message ent) {
        if(ent != null){
            em.persist(ent);
        }else{
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public void update(Message ent) {
        if(ent != null){
            em.merge(ent);
        }else{
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public void delete(Message ent) {
        if(ent != null){
            em.remove(read(ent.getId()));
        }else{
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public Message read(int entId) {
        Message ue = em.find(Message.class, entId);
        if(ue != null){
            return ue;
        }
        return null;
    }

    public List<Message> readFromTo(int start, int end, boolean archived) {
        Query q = em.createNamedQuery("Message.findAll");
        q.setParameter("archived", archived);
        if (start != -1 && end != -1) {
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return (List<Message>)q.getResultList();
    }


    public List<Message> getRelatedToNalog(int nalogId, boolean archived) {
        Query q = em.createNamedQuery("Message.getByNalog");
        q.setParameter("archived", archived);
        q.setParameter("uid", nalogId);
        return (List<Message>) q.getResultList();
    }


    public List<Message> getRelatedToZahtevek(int zahtevekId, boolean archived) {
        Query q = em.createNamedQuery("Message.getByZahtevek");
        q.setParameter("archived", archived);
        q.setParameter("uid", zahtevekId);
        List<Message> rtrns = q.getResultList();
        return (List<Message>) q.getResultList();
    }

    public List<Message> getUsersOutgoing(int userId, boolean archived) {
        Query q = em.createNamedQuery("Message.getUserOutgoing");
        q.setParameter("archived", archived);
        q.setParameter("uid", userId);
        return (List<Message>) q.getResultList();
    }


    public List<Message> getUsersIncoming(int userId, boolean archived) {
        Query q = em.createNamedQuery("Message.getUserIncoming");
        q.setParameter("archived", archived);
        q.setParameter("uid", userId);
        return (List<Message>) q.getResultList();
    }
}
