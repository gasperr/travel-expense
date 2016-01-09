package si.fri.sp.utils;

import si.fri.sp.entities.Message;

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

    public static String beautifyDate(Date date){
        if(date != null){
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.format(date);
        }else{
            return "";
        }
    }

    public static void sortMessages(List<Message> messages){
        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message d1, Message d2) {
                Date date1 = d1.getDate();
                Date date2 = d2.getDate();
                return date2.compareTo(date1);

            }
        });
    }
}
