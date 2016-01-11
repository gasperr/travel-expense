package si.fri.sp.managment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.fri.ApplicationCache;
import si.fri.LoggerExpense;
import si.fri.sp.entities.Nalog;
import si.fri.sp.entities.User;
import si.fri.sp.entities.Zahtevek;
import si.fri.sp.entities.enums.LogEnum;
import si.fri.sp.entities.enums.NalogStatus;
import si.fri.sp.entities.enums.UserType;
import si.fri.sp.entities.enums.ZahtevekStatus;
import si.fri.sp.interfaces.ZahtevekServiceLocal;
import si.fri.sp.utils.PermissionChecker;
import si.fri.sp.utils.Utils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NoPermissionException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Gasper Andrejc, created on 09/jan/2016
 */

@ViewScoped
@Named
public class ManagmentDashboard implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagmentDashboard.class);

    @Inject
    private ApplicationCache applicationCache;

    @Inject
    private PermissionChecker permissionChecker;

    @Inject
    private LoggerExpense loggerExpense;

    @EJB
    private ZahtevekServiceLocal zahtevekServiceLocal;

    private User user;

    private List<Zahtevek> waitingZahtevki = new ArrayList<>();

    private List<Nalog> currentNalogi = new ArrayList<>();

    private Nalog selectedNalog;

    private Zahtevek selectedZahtevek;

    private String responseToZahtevek;

    @PostConstruct
    private void beanInit(){
        try {
            user = permissionChecker.getCurrentUser();
        } catch (NoPermissionException e) {
            LOGGER.error("User could not be determined!", e);
        }
        initZahtevki();
        initNalogi();
    }

    public void openNalog(Nalog nalog){
        selectedNalog = nalog;
    }
    public void openZahtevek(Zahtevek zahtevek){
        selectedZahtevek = zahtevek;
    }


    public void approveZahtevek(Zahtevek zahtevek){

        zahtevek.setStatus(ZahtevekStatus.APPROVED);
        zahtevek.setNoteByManagment(this.responseToZahtevek);
        zahtevekServiceLocal.update(zahtevek);

        // create nalog
        Nalog nalog = Utils.createNalogFromZahtevek(zahtevek, user, responseToZahtevek == null ? "":responseToZahtevek);
        applicationCache.addNalog(nalog);
        zahtevek.setNalog(nalog);

        loggerExpense.log("Nalog " + nalog.getId() + " created from zahtevek " + selectedZahtevek.getId() + " by user " + user.getId(), LogEnum.MANAGMENT_NALOG);

        selectedZahtevek = null;

        applicationCache.clearZahtevekCache();
        waitingZahtevki.remove(zahtevek);

    }
    public void declineZahtevek(Zahtevek zahtevek){

        zahtevek.setStatus(ZahtevekStatus.DECLINED);
        zahtevek.setNoteByManagment(this.responseToZahtevek);
        zahtevekServiceLocal.update(zahtevek);


        loggerExpense.log("Zahtevek " + zahtevek.getId() + " declined by user " + user.getId() +" with reason: "+this.responseToZahtevek.substring(0, 20)+"...", LogEnum.MANAGMENT_ZAHTEVEK);

        selectedZahtevek = null;

        applicationCache.clearZahtevekCache();
        waitingZahtevki.remove(zahtevek);

    }

    private void initZahtevki(){
        List<Zahtevek> zahtevki = null;
        try{
            zahtevki = applicationCache.getZahtevki();
            for (Zahtevek zaht : zahtevki){
                if(zaht.getStatus() == ZahtevekStatus.IN_REVIEW){
                    waitingZahtevki.add(zaht);
                }
            }
        }catch (Exception e){
            LOGGER.error("Error trying to fetch all zahtevki", e);
        }
    }

    private void initNalogi(){
        List<Nalog> nalogi = null;
        try{
            nalogi = applicationCache.getNalogi();
            for(Nalog nalog : nalogi){
                if(Utils.nalogIsActive(nalog) && (nalog.getStatus() == NalogStatus.ACTIVE || nalog.getStatus() == NalogStatus.APPROVED)){
                    currentNalogi.add(nalog);
                }
            }
        }catch (Exception e){
            LOGGER.error("Error trying to fetch all nalogi", e);
        }
    }

    public String getDate(Date date){
        return Utils.beautifyDate(date);
    }

    public double calculateSpent(Nalog nalog){
        return Utils.calculateSpent(nalog);
    }

    public List<Zahtevek> getWaitingZahtevki() {
        return waitingZahtevki;
    }

    public List<Nalog> getCurrentNalogi() {
        return currentNalogi;
    }

    public Nalog getSelectedNalog() {
        return selectedNalog;
    }

    public void setSelectedNalog(Nalog selectedNalog) {
        this.selectedNalog = selectedNalog;
    }

    public Zahtevek getSelectedZahtevek() {
        return selectedZahtevek;
    }

    public void setSelectedZahtevek(Zahtevek selectedZahtevek) {
        this.selectedZahtevek = selectedZahtevek;
    }

    public boolean userIsManagment() {
        return user.getType() == UserType.MANAGER;
    }

    public String getResponseToZahtevek() {
        return responseToZahtevek;
    }

    public void setResponseToZahtevek(String responseToZahtevek) {
        this.responseToZahtevek = responseToZahtevek;
    }
}
