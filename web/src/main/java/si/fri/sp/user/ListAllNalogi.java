package si.fri.sp.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.fri.ApplicationCache;
import si.fri.sp.entities.Message;
import si.fri.sp.entities.Nalog;
import si.fri.sp.entities.User;
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
public class ListAllNalogi implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListAllNalogi.class);

    @Inject
    private ApplicationCache applicationCache;

    private User user;

    private List<Nalog> allNalogi;

    private Nalog selectedNalog;

    private Nalog connectedSelectedNalog;

    @PostConstruct
    private void beanInit(){
        user = new User();
        user.setId(1);
    }

    public List<Message> getConnectedMessages(){
        List<Message> cm = null;
        try {
            cm = applicationCache.getMessageByNalog(connectedSelectedNalog);
            Utils.sortMessages(cm);
        } catch (Exception e) {
            LOGGER.error("Error while trying to fetch connected messages", e);
        }
        return cm;
    }

    public void openNalog(Nalog nalog){
        selectedNalog = nalog;
    }
    public void closeNalog() {
        if (this.selectedNalog != null) {
            this.selectedNalog = null;
        }
    }

    public void openConnectedSelectedNalog(Nalog nalog){
            connectedSelectedNalog = nalog;
    }
    public void closeConnectedSelectedNalog(){
        if(this.connectedSelectedNalog != null){
            this.connectedSelectedNalog = null;
        }
    }

    public Nalog getSelectedNalog() {
        return selectedNalog;
    }

    public void setSelectedNalog(Nalog selectedNalog) {
        this.selectedNalog = selectedNalog;
    }

    public Nalog getConnectedSelectedNalog() {
        return connectedSelectedNalog;
    }

    public void setConnectedSelectedNalog(Nalog connectedSelectedNalog) {
        this.connectedSelectedNalog = connectedSelectedNalog;
    }

    public List<Nalog> getAllNalogi(){
        if(allNalogi == null){
            try {
                allNalogi = applicationCache.getUserNalogs(user, false);
            } catch (Exception e) {
                LOGGER.error("Error while trying to fetch user's nalogs", e);
            }
        }
        return allNalogi;
    }

    public String getDate(Date date){
        return Utils.beautifyDate(date);
    }
}
