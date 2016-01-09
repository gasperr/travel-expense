package si.fri.sp.entities.enums;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
public enum ZahtevekStatus {
    APPROVED ("Odobren"),
    DECLINED ("Zavrnjen"),
    IN_REVIEW ("Čakajoč");

    private final String stringValue;

    ZahtevekStatus(String s) {
        stringValue = s;
    }

    public String getStringValue(){
        return this.stringValue;
    }

}
