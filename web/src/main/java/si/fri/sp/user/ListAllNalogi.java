package si.fri.sp.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.fri.ApplicationCache;
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

    @PostConstruct
    private void beanInit(){
        user = new User();
        user.setId(1);
    }

    public List<Nalog> getAllNalogi(){
        if(allNalogi == null){
            allNalogi = applicationCache.getUserNalogs(user, false);
        }
        return allNalogi;
    }

    public String getDate(Date date){
        return Utils.beautifyDate(date);
    }
}
