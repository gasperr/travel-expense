package si.fri.sp.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
