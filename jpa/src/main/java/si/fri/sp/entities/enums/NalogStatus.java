package si.fri.sp.entities.enums;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
public enum NalogStatus {
    APPROVED("Odobren"),
    DECLINED("Zavrnjen"),
    EXECUTED("Izvršen"),
    ACTIVE("Aktiven");

    private final String stringValue;

    NalogStatus(String s) {
        stringValue = s;
    }

    public String getStringValue(){
        return this.stringValue;
    }
}
