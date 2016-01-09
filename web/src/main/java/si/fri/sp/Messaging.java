package si.fri.sp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.fri.ApplicationCache;
import si.fri.sp.entities.Message;
import si.fri.sp.entities.User;
import si.fri.sp.utils.Utils;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Gasper Andrejc, created on 09/jan/2016
 */

@ViewScoped
@Named
public class Messaging implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Messaging.class);

    @Inject
    private ApplicationCache applicationCache;

    private User user;

    private Message selectedMessage;

    @PostConstruct
    private void beanInit(){
        user = new User();
        user.setId(1);
    }


    public List<Message> getMessageList() {
        List<Message> rtrn = new ArrayList<>();
        try {
            rtrn = applicationCache.getMessagesByUser(user);
        } catch (Exception e) {
            LOGGER.error("Error trying to fetch user's messages..", e);
        }

        return rtrn;
    }

    public void openConnected(Message message){
        selectedMessage = message;
    }
    public void closeConnected() {
        if (this.selectedMessage != null) {
            this.selectedMessage = null;
        }
    }

    public String getDate(Date date){
        return Utils.beautifyDate(date);
    }

    public User getUser() {
        return user;
    }

    public Message getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(Message selectedMessage) {
        this.selectedMessage = selectedMessage;
    }
}
