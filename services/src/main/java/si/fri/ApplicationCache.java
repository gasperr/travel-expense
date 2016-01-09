package si.fri;

import si.fri.sp.entities.Nalog;
import si.fri.sp.entities.User;
import si.fri.sp.entities.Zahtevek;
import si.fri.sp.interfaces.MessageServiceLocal;
import si.fri.sp.interfaces.NalogServiceLocal;
import si.fri.sp.interfaces.UserServiceLocal;
import si.fri.sp.interfaces.ZahtevekServiceLocal;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Gasper Andrejc, created on 09/jan/2016
 */

@ApplicationScoped
public class ApplicationCache implements Serializable {

    @EJB
    private UserServiceLocal userServiceLocal;

    @EJB
    private ZahtevekServiceLocal zahtevekServiceLocal;

    @EJB
    private NalogServiceLocal nalogServiceLocal;

    @EJB
    private MessageServiceLocal messageServiceLocal;


    private Map<Integer, Nalog> nalogCache = new HashMap<>();
    private Map<Integer, List<Nalog>> nalogUserCache = new HashMap<>();

    private Map<Integer, Zahtevek> zahtevekCache = new HashMap<>();
    private Map<Integer, List<Zahtevek>> zahtevekUserCache = new HashMap<>();

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
    public List<Nalog> getUserNalogs(User user, boolean archived){
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
    public List<Zahtevek> getUserZahtevek(User user, boolean archived){
        if(zahtevekUserCache.containsKey(user.getId())){
            return zahtevekUserCache.get(user.getId());
        }else{
            List<Zahtevek> zahteveks = zahtevekServiceLocal.getAllByUserId(user.getId(), archived);
            if(!zahteveks.isEmpty()){
                zahtevekUserCache.put(user.getId(), zahteveks);
                return zahteveks;
            }else{
                return new ArrayList<>();
            }
        }
    }

}
