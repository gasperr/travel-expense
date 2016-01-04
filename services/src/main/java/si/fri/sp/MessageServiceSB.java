package si.fri.sp;

import si.fri.sp.entities.MessageEntity;
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

    public void create(MessageEntity ent) {
        if(ent != null){
            em.persist(ent);
        }else{
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public void update(MessageEntity ent) {
        if(ent != null){
            em.merge(ent);
        }else{
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public void delete(MessageEntity ent) {
        if(ent != null){
            em.remove(read(ent.getId()));
        }else{
            throw new IllegalArgumentException("Passed entity is null");
        }
    }

    public MessageEntity read(int entId) {
        MessageEntity ue = em.find(MessageEntity.class, entId);
        if(ue != null){
            return ue;
        }
        return null;
    }

    public List<MessageEntity> readFromTo(int start, int end) {
        Query q = em.createNamedQuery("Message.findAll");
        if (start != -1 && end != -1) {
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return (List<MessageEntity>)q.getResultList();
    }


    public List<MessageEntity> getRelatedToNalog(int nalogId) {
        Query q = em.createNamedQuery("Message.getByNalog");
        q.setParameter("uid", nalogId);
        return (List<MessageEntity>) q.getResultList();
    }


    public List<MessageEntity> getRelatedToZahtevek(int zahtevekId) {
        Query q = em.createNamedQuery("Message.getByZahtevek");
        q.setParameter("uid", zahtevekId);
        List<MessageEntity> rtrns = q.getResultList();
        return (List<MessageEntity>) q.getResultList();
    }

    public List<MessageEntity> getUsersOutgoing(int userId) {
        Query q = em.createNamedQuery("Message.getUserOutgoing");
        q.setParameter("uid", userId);
        return (List<MessageEntity>) q.getResultList();
    }


    public List<MessageEntity> getUsersIncoming(int userId) {
        Query q = em.createNamedQuery("Message.getUserIncoming");
        q.setParameter("uid", userId);
        return (List<MessageEntity>) q.getResultList();
    }
}
