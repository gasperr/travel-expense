package si.fri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.fri.sp.LoggerService;
import si.fri.sp.entities.*;
import si.fri.sp.entities.enums.LogEnum;
import si.fri.sp.interfaces.*;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

/**
 * @Author Gasper Andrejc, created on 09/jan/2016
 */

@ApplicationScoped
public class ApplicationCache implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationCache.class);

    @EJB
    private UserServiceLocal userServiceLocal;

    @EJB
    private ZahtevekServiceLocal zahtevekServiceLocal;

    @EJB
    private NalogServiceLocal nalogServiceLocal;

    @EJB
    private MessageServiceLocal messageServiceLocal;

    @EJB
    private LoggerLocal loggerExpense;


    private Map<Integer, Nalog> nalogCache = new HashMap<>();
    private Map<Integer, List<Nalog>> nalogUserCache = new HashMap<>();

    private Map<Integer, Zahtevek> zahtevekCache = new HashMap<>();
    private Map<Integer, List<Zahtevek>> zahtevekUserCache = new HashMap<>();

    private Map<Integer, List<Message>> nalogMessageCache = new HashMap<>();
    private Map<Integer, List<Message>> userMessageCache = new HashMap<>();

    private Set<Log> logSet = new HashSet<>();

    @PreDestroy
    private void preDestroy(){
        persistLogs();
    }

    public void persistLogs(){
        LOGGER.info("Started persisting logs...");
        for (Log log : logSet){
            loggerExpense.create(log);
        }
        logSet = new HashSet<>();
        LOGGER.info("All logs successfully persisted...");
    }

    public void log(Log log){
        logSet.add(log);
    }




    public List<Message> getMessagesByUser(User user) throws Exception{
        if(user != null){
            if(userMessageCache.containsKey(user.getId())){
                return userMessageCache.get(user.getId());
            }else{
                List<Message> msgs = messageServiceLocal.getUsersIncoming(user.getId(), false);
                msgs.addAll(messageServiceLocal.getUsersOutgoing(user.getId(), false));
                if(!msgs.isEmpty()){
                    userMessageCache.put(user.getId(), msgs);
                }
                return msgs;
            }
        }else{
            throw new Exception("Wanting to access cache with null value");
        }
    }


    public List<Message> getMessageByNalog(Nalog nalog) throws Exception{
        if(nalog != null){
            if(nalogMessageCache.containsKey(nalog.getId())){
                return nalogMessageCache.get(nalog.getId());
            }else{
                List<Message> msgs = messageServiceLocal.getRelatedToNalog(nalog.getId(), true);
                msgs.addAll(messageServiceLocal.getRelatedToNalog(nalog.getId(), false));
                if(!msgs.isEmpty()){
                    nalogMessageCache.put(nalog.getId(), msgs);
                }
                return msgs;
            }
        }else{
            throw new Exception("Wanting to access cache with null value");
        }
    }

    public Nalog getNalog(Integer id){
        if(nalogCache.containsKey(id)){
            return nalogCache.get(id);
        }else{
            Nalog nalog = nalogServiceLocal.read(id);
            if(nalog != null){
                nalogCache.put(id, nalog);
                return nalog;
            }else{
                return null;
            }
        }
    }
    public List<Nalog> getNalogi(){
        List<Nalog> nalogi = nalogServiceLocal.readFromTo(-1, -1, false);
        for(Nalog nlg : nalogi){
            nalogCache.put(nlg.getId(), nlg);
        }
        return nalogi;

    }
    public List<Nalog> getUserNalogs(User user, boolean archived) throws Exception{
        if(user != null){
            if(nalogUserCache.containsKey(user.getId())){
                return nalogUserCache.get(user.getId());
            }else{
                List<Nalog> nalogs = nalogServiceLocal.getAllByUserId(user.getId(), archived);
                if(!nalogs.isEmpty()){
                    nalogUserCache.put(user.getId(), nalogs);
                    return nalogs;
                }else{
                    return new ArrayList<>();
                }
            }
        }else{
            throw new Exception("Wanting to access cache with null value");
        }
    }
    public Zahtevek getZahtevek(Integer id){
        if(zahtevekCache.containsKey(id)){
            return zahtevekCache.get(id);
        }else{
            Zahtevek zahtevek = zahtevekServiceLocal.read(id);
            if(zahtevek != null){
                zahtevekCache.put(id, zahtevek);
                return zahtevek;
            }else{
                return null;
            }
        }
    }
    public List<Zahtevek> getZahtevki(){
        if(zahtevekCache.isEmpty()){
            List<Zahtevek> zahtevki = zahtevekServiceLocal.readFromTo(-1, -1, false);
            for(Zahtevek zaht : zahtevki){
                zahtevekCache.put(zaht.getId(), zaht);
            }
            return zahtevki;
        }else{
            ArrayList<Zahtevek> arList = new ArrayList<Zahtevek>();

            for(Map.Entry<Integer,Zahtevek> map : zahtevekCache.entrySet()){

                arList.add(map.getValue());

            }
            return arList;
        }


    }
    public List<Zahtevek> getUserZahtevek(User user, boolean archived) throws Exception{
        if(user != null){
            if (zahtevekUserCache.containsKey(user.getId())) {
                List<Zahtevek> all = zahtevekUserCache.get(user.getId());
                if (!archived) {

                    Iterator<Zahtevek> iter = all.iterator();
                    while (iter.hasNext()) {
                        Zahtevek zahtevek = iter.next();
                        if (zahtevek.isArchived()){
                            iter.remove();
                        }
                    }

                }
                return all;
            } else {
                List<Zahtevek> zahteveks = zahtevekServiceLocal.getAllByUserId(user.getId(), archived);
                if(!zahteveks.isEmpty()){
                    zahtevekUserCache.put(user.getId(), zahteveks);
                    return zahteveks;
                }else{
                    return new ArrayList<>();
                }
            }
        }else{
            throw new Exception("Wanting to access cache with null value");
        }
    }

    public List<Zahtevek> getReviewedByZahtevki(User user){
        return zahtevekServiceLocal.getAllByReviewedBy(user.getId(), true);
    }

    public void addNalog(Nalog nalog){
        nalogServiceLocal.create(nalog);
        nalogCache.put(nalog.getId(), nalog);
    }
    public void addZahtevek(User user, Zahtevek zahtevek){
        zahtevekServiceLocal.create(zahtevek);
        if(zahtevekUserCache.containsKey(user.getId())){
            zahtevekUserCache.get(user.getId()).add(zahtevek);
        }else{
            List<Zahtevek> tmp = new ArrayList<>();
            tmp.add(zahtevek);
            zahtevekUserCache.put(user.getId(), tmp);
        }
    }
    public void addMessage(Message message){
        messageServiceLocal.create(message);
        if(userMessageCache.containsKey(message.getFromUser().getId())){
            userMessageCache.get(message.getFromUser().getId()).add(message);
        }else if(userMessageCache.containsKey(message.getToUser().getId())){
            userMessageCache.get(message.getToUser().getId()).add(message);
        }else{
            List<Message> nm = new ArrayList<>();
            nm.add(message);
            userMessageCache.put(message.getFromUser().getId(), nm);
        }
    }

    public void clearAllCache(){
        nalogCache = new HashMap<>();
        nalogUserCache = new HashMap<>();

        zahtevekCache = new HashMap<>();
        zahtevekUserCache = new HashMap<>();

        nalogMessageCache = new HashMap<>();
        userMessageCache = new HashMap<>();

    }

    public void clearNalogiCache(){
        nalogCache = new HashMap<>();
        nalogUserCache = new HashMap<>();
    }

    public void clearZahtevekCache(){
        zahtevekCache = new HashMap<>();
        zahtevekUserCache = new HashMap<>();
    }



}
