package si.fri.sp.utils.aclImpl;

import java.security.acl.Permission;

/**
 * @Author Gasper Andrejc, created on 10/jan/2016
 */
public class PermissionImpl implements Permission {
    private String permission;

    public PermissionImpl(String var1) {
        this.permission = var1;
    }

    public boolean equals(Object var1) {
        if(var1 instanceof Permission) {
            Permission var2 = (Permission)var1;
            return this.permission.equals(var2.toString());
        } else {
            return false;
        }
    }

    public String toString() {
        return this.permission;
    }

    public int hashCode() {
        return this.toString().hashCode();
    }
}
