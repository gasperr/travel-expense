package si.fri;

import si.fri.sp.entities.Log;
import si.fri.sp.entities.enums.LogEnum;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author Gasper Andrejc, created on 12/jan/2016
 */

@RequestScoped
@Named
public class LoggerExpense implements Serializable {

    @Inject
    private ApplicationCache applicationCache;

    public void log(String logS, LogEnum logEnum){
        Log log = new Log();
        log.setDate(new Timestamp(new Date().getTime()));
        log.setLog(logS);
        log.setType(logEnum);

        applicationCache.log(log);
    }


}
