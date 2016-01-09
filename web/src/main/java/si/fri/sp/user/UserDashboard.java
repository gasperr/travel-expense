package si.fri.sp.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.fri.ApplicationCache;
import si.fri.sp.entities.Nalog;
import si.fri.sp.entities.User;
import si.fri.sp.entities.Zahtevek;
import si.fri.sp.utils.Utils;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author Gasper Andrejc, created on 09/jan/2016
 */

@ViewScoped
@Named
public class UserDashboard implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDashboard.class);

    @Inject
    private ApplicationCache applicationCache;

    private Nalog currentNalog;

    private List<Zahtevek> myZahtevki;

    private User currentUser;

    private Zahtevek selectedZahtevek;


    @PostConstruct
    private void dashboardInit(){
        currentUser = new User();
        currentUser.setId(1);

        initCurrentNalog();
        initZahtevki();
    }

    private void initCurrentNalog(){
        currentNalog = applicationCache.getNalog(1);
    }

    private void initZahtevki(){
        try {
            myZahtevki = applicationCache.getUserZahtevek(currentUser, false);
        } catch (Exception e) {
            LOGGER.error("Error trying to fetch user's zahtevki", e);
        }
    }

    public String getDate(Date date){
        return Utils.beautifyDate(date);
    }

    public void openZahtevek(Zahtevek zahtevek){
        selectedZahtevek = zahtevek;
    }
    public void closeZahtevek(){
        if(this.selectedZahtevek != null){
            this.selectedZahtevek = null;
        }
    }


    public Zahtevek getSelectedZahtevek() {
        return selectedZahtevek;
    }

    public Nalog getCurrentNalog() {
        return currentNalog;
    }

    public void setCurrentNalog(Nalog currentNalog) {
        this.currentNalog = currentNalog;
    }

    public List<Zahtevek> getMyZahtevki() {
        return myZahtevki;
    }

}
