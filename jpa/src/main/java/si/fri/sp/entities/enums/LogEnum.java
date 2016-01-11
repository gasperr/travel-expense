package si.fri.sp.entities.enums;

/**
 * @Author Gasper Andrejc, created on 11/jan/2016
 */
public enum LogEnum {
    USER_ZAHTEVEK("user_zht"),
    USER_NALOG("user_nlg"),
    MANAGMENT_ZAHTEVEK("mngmnt_zht"),
    MANAGMENT_NALOG("mngmt_nlg"),
    FINANCE("finance");

    private String type;

    LogEnum(String string) {
        this.type = string;
    }

    public String getString(){
        return this.type;
    }
}
