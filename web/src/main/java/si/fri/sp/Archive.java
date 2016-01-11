package si.fri.sp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.fri.ApplicationCache;
import si.fri.sp.entities.Message;
import si.fri.sp.entities.Nalog;
import si.fri.sp.entities.User;
import si.fri.sp.entities.Zahtevek;
import si.fri.sp.utils.PermissionChecker;
import si.fri.sp.utils.Utils;

import javax.annotation.PostConstruct;
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
public class Archive implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Archive.class);

    @Inject
    private ApplicationCache applicationCache;

    @Inject
    private PermissionChecker permissionChecker;

    private User user;

    @PostConstruct
    private void beanInit(){
        try {
            user = permissionChecker.getCurrentUser();
        } catch (NoPermissionException e) {
            LOGGER.error("User could not be determined!", e);
        }
    }


    public String getDate(Date date){
        return Utils.beautifyDate(date);
    }

    public List<Message> getArchivedMessages(){
        List<Message> rtrn = new ArrayList<>();
        try {
            rtrn = applicationCache.getMessagesByUser(user);
        } catch (Exception e) {
            LOGGER.error("Error trying to fetch archived messages", e);
        }

        return rtrn;
    }

    public List<Zahtevek> getArchivedZahtevki(){
        List<Zahtevek> rtrn = new ArrayList<>();
        try {
            rtrn = applicationCache.getUserZahtevek(user, true);
        } catch (Exception e) {
            LOGGER.error("Error trying to fetch archived messages", e);
        }

        return rtrn;
    }

    public List<Nalog> getArchivedNalogi(){
        List<Nalog> rtrn = new ArrayList<>();
        try {
            rtrn = applicationCache.getUserNalogs(user, true);
        } catch (Exception e) {
            LOGGER.error("Error trying to fetch archived messages", e);
        }

        return rtrn;
    }

}
