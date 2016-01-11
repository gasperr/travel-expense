package si.fri.sp.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.fri.ApplicationCache;
import si.fri.LoggerExpense;
import si.fri.sp.entities.Message;
import si.fri.sp.entities.Nalog;
import si.fri.sp.entities.ServiceEntity;
import si.fri.sp.entities.User;
import si.fri.sp.entities.enums.LogEnum;
import si.fri.sp.entities.enums.NalogStatus;
import si.fri.sp.interfaces.NalogServiceLocal;
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
 * @Author Gasper Andrejc, created on 10/jan/2016
 */

@ViewScoped
@Named
public class FinanceDashboard implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceDashboard.class);

    @Inject
    private ApplicationCache applicationCache;

    @Inject
    private PermissionChecker permissionChecker;

    @Inject
    private LoggerExpense loggerExpense;

    @EJB
    private NalogServiceLocal nalogServiceLocal;

    private User user;

    private List<Nalog> waitingNalogi;

    private Nalog inProgress;

    private List<ServiceEntity> selectedService = new ArrayList<>();

    private boolean toReviewNosilcu;
    private boolean toReviewPotrjevalcu;

    private String generalMessage;





    @PostConstruct
    private void beanInit(){
        try {
            user = permissionChecker.getCurrentUser();
        } catch (NoPermissionException e) {
            LOGGER.error("User could not be determined!", e);
        }

        initWaitingNalog();
        initInProgress();

    }

    public void addSelectedService(ServiceEntity entity){
        if(!selectedService.contains(entity)){
            selectedService.add(entity);
        }
    }


    /**
     * finishes nalog and sends it to archive so user can see it there
     */
    public void executeNalog(){
        if(this.inProgress != null){
            inProgress.setExecutedOn(new Date());
            inProgress.setStatus(NalogStatus.EXECUTED);
            inProgress.setArchived(true);

            nalogServiceLocal.update(inProgress);

            Message message = new Message();
            message.setFromUser(user);
            message.setToUser(inProgress.getOwner());
            message.setSubject("[Racunovodstvo] Nalog " + inProgress.getId() + " izvrsen");
            message.setNalogRelated(inProgress);
            message.setZahtevekRelated(inProgress.getZahtevek());
            message.setContent("Dne " + getDate(new Date()) + " je bil nalog stevilka " + inProgress.getId() + " odobren. Najdete ga v svojem arhivu nalogov. Lep pozdrav, racunovodstvo");
            message.setDate(new Date());

            applicationCache.addMessage(message);
            loggerExpense.log("Nalog " + inProgress.getId() + " executed by user " + user.getId(), LogEnum.FINANCE);



            refreshWaitingList();
            initInProgress();


        }
    }

    private void initWaitingNalog(){
        waitingNalogi = new ArrayList<>();
        List<Nalog> nalogi = applicationCache.getNalogi();
        for (Nalog nalog : nalogi){
            if(nalog.getStatus() == NalogStatus.FINISHED){
                waitingNalogi.add(nalog);
            }
        }
    }

    private void initInProgress(){
        if(!this.waitingNalogi.isEmpty()){
            this.inProgress = waitingNalogi.get(0);
        }else{
            this.inProgress = null;
        }
    }

    public void sendToReview(){
        Message message = new Message();
        message.setFromUser(user);
        message.setToUser(toReviewNosilcu ? inProgress.getApprovedBy() : inProgress.getOwner());
        message.setSubject("[Racunovodstvo] Please review");
        message.setNalogRelated(inProgress);
        message.setZahtevekRelated(inProgress.getZahtevek());

        loggerExpense.log("Nalog " + inProgress.getId() + " sent to review to " + (toReviewNosilcu ? inProgress.getApprovedBy().getId() : inProgress.getOwner().getId()), LogEnum.FINANCE);


        StringBuilder sb = new StringBuilder();
        for(ServiceEntity serviceEntity : selectedService){
            sb.append(serviceEntity.toString()+"\n");
        }

        message.setContent("General message: "+generalMessage+"\n Noted services: "+sb.toString());

        message.setDate(new Date());

        applicationCache.addMessage(message);

        toReviewNosilcu = false;
        toReviewPotrjevalcu = false;

    }

    public void refreshWaitingList(){
        applicationCache.clearAllCache();
        initWaitingNalog();
    }

    public void closePopup(){
        setToReviewNosilcu(false);
        setToReviewPotrjevalcu(false);

    }

    public String getDate(Date date){
        return Utils.beautifyDate(date);
    }

    public List<ServiceEntity> getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(List<ServiceEntity> selectedService) {
        this.selectedService = selectedService;
    }

    public double calculate(Nalog nalog){
        return Utils.calculateSpent(nalog);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Nalog> getWaitingNalogi() {
        return waitingNalogi;
    }

    public void setWaitingNalogi(List<Nalog> waitingNalogi) {
        this.waitingNalogi = waitingNalogi;
    }

    public boolean isToReviewNosilcu() {
        return toReviewNosilcu;
    }

    public void setToReviewNosilcu(boolean toReviewNosilcu) {
        this.toReviewNosilcu = toReviewNosilcu;
    }

    public boolean isToReviewPotrjevalcu() {
        return toReviewPotrjevalcu;
    }

    public void setToReviewPotrjevalcu(boolean toReviewPotrjevalcu) {
        this.toReviewPotrjevalcu = toReviewPotrjevalcu;
    }

    public Nalog getInProgress() {
        return inProgress;
    }

    public void setInProgress(Nalog inProgress) {
        this.inProgress = inProgress;
    }

    public String getGeneralMessage() {
        return generalMessage;
    }

    public void setGeneralMessage(String generalMessage) {
        this.generalMessage = generalMessage;
    }
}
