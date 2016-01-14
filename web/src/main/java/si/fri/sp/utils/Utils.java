package si.fri.sp.utils;

import si.fri.sp.entities.*;
import si.fri.sp.entities.enums.NalogStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @Author Gasper Andrejc, created on 09/jan/2016
 */
public class Utils {

    /**
     * @param date
     * @return human readable date
     */
    public static String beautifyDate(Date date){
        if(date != null){
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.format(date);
        }else{
            return "";
        }
    }

    /**
     * @param nalog that we want to check if is active
     * @return true if nalog is active
     */
    public static boolean nalogIsActive(Nalog nalog){
        if((new Date().compareTo(nalog.getFromDate()) >= 0) && (new Date().compareTo(nalog.getToDate()) <= 0) && (nalog.getStatus() != NalogStatus.FINISHED)){
            nalog.setStatus(NalogStatus.ACTIVE);
            return true;
        }
        return false;
    }

    /**
     * sort masages in ascending order by date of creation
     * @param messages
     */
    public static void sortMessages(List<Message> messages){
        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message d1, Message d2) {
                Date date1 = d1.getDate();
                Date date2 = d2.getDate();
                return date1.compareTo(date2);

            }
        });
    }

    public static void sortZahtevki(List<Zahtevek> zahteveks){
        Collections.sort(zahteveks, new Comparator<Zahtevek>() {
            @Override
            public int compare(Zahtevek d1, Zahtevek d2) {
                Date date1 = d1.getToDate();
                Date date2 = d2.getToDate();
                return date2.compareTo(date1);

            }
        });
    }

    /**
     * calculates total amount of money spent by nalog's services
     * @param nalog
     * @return total amount of spent
     */
    public static double calculateSpent(Nalog nalog){
        if(nalog == null){
            return 0;
        }
        double rtrn = 0;
        for(ServiceEntity serviceEntity : nalog.getServices()){
            rtrn += serviceEntity.getPrice();
        }
        return rtrn;
    }

    /**
     * creates nalog from zahtevek
     * @param zahtevek - zahtevek
     * @param approvedBy - by whom it has been approved
     * @param notes - notes added by the one approving
     * @return
     */
    public static Nalog createNalogFromZahtevek(Zahtevek zahtevek, User approvedBy, String notes){

        Nalog nalog = new Nalog();
        nalog.setApprovedBy(approvedBy);
        nalog.setArchived(false);
        nalog.setContent(zahtevek.getContent());
        nalog.setCovered(zahtevek.getCosts());
        nalog.setFromDate(zahtevek.getFromDate());
        nalog.setToDate(zahtevek.getToDate());
        nalog.setFromDate(zahtevek.getFromDate());
        nalog.setLocation(zahtevek.getLocation());
        nalog.setOwner(zahtevek.getOwner());
        nalog.setNotes(notes);
        nalog.setStatus(NalogStatus.APPROVED);
        nalog.setZahtevek(zahtevek);
        nalog.setPurpose(zahtevek.getLocation());

        return nalog;
    }
}
