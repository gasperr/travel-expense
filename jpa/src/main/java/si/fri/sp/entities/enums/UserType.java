package si.fri.sp.entities.enums;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
public enum UserType {
    DRIVER("Voznik"),
    MANAGER("Managment"),
    FINANCE("Računovodstvo");

    private final String stringValue;

    UserType(String s) {
        stringValue = s;
    }

    public String getStringValue(){
        return this.stringValue;
    }
}
