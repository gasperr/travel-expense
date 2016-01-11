package si.fri.sp.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.fri.ApplicationCache;
import si.fri.LoggerExpense;
import si.fri.sp.entities.Nalog;
import si.fri.sp.entities.ServiceEntity;
import si.fri.sp.entities.User;
import si.fri.sp.entities.Zahtevek;
import si.fri.sp.entities.enums.LogEnum;
import si.fri.sp.entities.enums.NalogStatus;
import si.fri.sp.interfaces.NalogServiceLocal;
import si.fri.sp.interfaces.ServiceServiceLocal;
import si.fri.sp.interfaces.ZahtevekServiceLocal;
import si.fri.sp.utils.PermissionChecker;
import si.fri.sp.utils.Utils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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
public class UserDashboard implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDashboard.class);

    @Inject
    private ApplicationCache applicationCache;

    @Inject
    private PermissionChecker permissionChecker;

    @Inject
    private LoggerExpense loggerExpense;

    @EJB
    private ServiceServiceLocal serviceServiceLocal;

    @EJB
    private ZahtevekServiceLocal zahtevekServiceLocal;

    @EJB
    private NalogServiceLocal nalogServiceLocal;

    private List<Nalog> activeNalogi = new ArrayList<>();
    private Nalog currentNalog;
    private List<ServiceEntity> serviceEntityList;

    private List<Zahtevek> myZahtevki;

    private User currentUser;

    private Zahtevek selectedZahtevek;

    private String addingNewType;
    private double addingNewPrice;
    private String addingNewNotes;

    private ZahtevekImpl newZahtevek;

    private Zahtevek lastZahtevek;



    @PostConstruct
    private void dashboardInit(){
        try {
            currentUser = permissionChecker.getCurrentUser();
        } catch (NoPermissionException e) {
            LOGGER.error("User could not be determined!", e);
        }

        initCurrentNalog();
        initZahtevki();

    }


    private void initCurrentNalog(){
        try {
            List<Nalog> allNalogi = applicationCache.getUserNalogs(currentUser, false);
            if(!allNalogi.isEmpty()){
                for(Nalog nalog : allNalogi){
                    if(Utils.nalogIsActive(nalog) && nalog.getStatus() == NalogStatus.APPROVED){
                        nalog.setStatus(NalogStatus.ACTIVE);
                        nalogServiceLocal.update(nalog);
                        activeNalogi.add(nalog);
                    }
                }
            }
            if(!activeNalogi.isEmpty()){
                currentNalog = activeNalogi.get(0);
                serviceEntityList = currentNalog.getServices();
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    /**
     * sends selected zahtevek to archive
     * @param zahtevek
     */
    public void toArchive(Zahtevek zahtevek){
        zahtevek.setArchived(true);
        zahtevekServiceLocal.update(zahtevek);
        initZahtevki();
    }

    public void switchCurrent(Nalog nalog){
        this.currentNalog = nalog;
        serviceEntityList = nalog.getServices();
    }

    private void initZahtevki(){
        try {
            myZahtevki = applicationCache.getUserZahtevek(currentUser, false);
            Utils.sortZahtevki(myZahtevki);
            this.lastZahtevek = myZahtevki.get(0);
        } catch (Exception e) {
            LOGGER.error("Error trying to fetch user's zahtevki", e);
        }
    }

    /**
     * inits new zahtevek so popup opens
     */
    public void initNewZahtevek(){
        this.newZahtevek = new ZahtevekImpl();
    }

    /***
     * creates new zahtevek from user's popup on dashboard
     */
    public void createNewZahtevek(){
        Zahtevek zahtevek = new Zahtevek();
        zahtevek.setContent(this.newZahtevek.getContent());

        loggerExpense.log("New zahtevek " + zahtevek.getId() + " created by user " + currentUser.getId(), LogEnum.USER_ZAHTEVEK);

    }

    /**
     * adds new service to current nalog
     */
    public void addNewService(){
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setNalog(this.currentNalog);
        serviceEntity.setNotes(addingNewNotes);
        serviceEntity.setPrice(addingNewPrice);
        serviceEntity.setType(addingNewType);



        serviceServiceLocal.create(serviceEntity);
        serviceEntityList.add(serviceEntity);

        addingNewNotes = "";
        addingNewPrice = 0;
        addingNewType = "";

        loggerExpense.log("Service added to nalog "+this.currentNalog.getId(), LogEnum.USER_NALOG);

    }

    /**
     * oddaja naloga v finance, sprememba status nalog
     */
    public void oddajNalog(){
        this.currentNalog.setStatus(NalogStatus.FINISHED);
        activeNalogi.remove(this.currentNalog);
        nalogServiceLocal.update(this.currentNalog);

        loggerExpense.log("Nalog "+currentNalog.getId()+" finished and sent to finance", LogEnum.USER_NALOG);

        if(!activeNalogi.isEmpty()){
            this.currentNalog = activeNalogi.get(0);
            this.serviceEntityList = this.currentNalog.getServices();
        }else{
            this.currentNalog = null;
            this.serviceEntityList = new ArrayList<>();
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

    public List<Nalog> getActiveNalogi() {
        return activeNalogi;
    }

    public void setActiveNalogi(List<Nalog> activeNalogi) {
        this.activeNalogi = activeNalogi;
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

    public String getAddingNewType() {
        return addingNewType;
    }

    public void setAddingNewType(String addingNewType) {
        this.addingNewType = addingNewType;
    }

    public double getAddingNewPrice() {
        return addingNewPrice;
    }

    public void setAddingNewPrice(double addingNewPrice) {
        this.addingNewPrice = addingNewPrice;
    }

    public String getAddingNewNotes() {
        return addingNewNotes;
    }

    public void setAddingNewNotes(String addingNewNotes) {
        this.addingNewNotes = addingNewNotes;
    }

    public List<ServiceEntity> getServiceEntityList() {
        return serviceEntityList;
    }

    public void setServiceEntityList(List<ServiceEntity> serviceEntityList) {
        this.serviceEntityList = serviceEntityList;
    }

    public ZahtevekImpl getNewZahtevek() {
        return newZahtevek;
    }

    public void setNewZahtevek(ZahtevekImpl newZahtevek) {
        this.newZahtevek = newZahtevek;
    }

    public String getLastZahtevek() {
        return Utils.beautifyDate(lastZahtevek.getToDate()) + ", " + lastZahtevek.getLocation();
    }


}
