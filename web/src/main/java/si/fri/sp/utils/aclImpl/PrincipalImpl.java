package si.fri.sp.utils.aclImpl;

import java.security.Principal;

/**
 * @Author Gasper Andrejc, created on 10/jan/2016
 */
public class PrincipalImpl implements Principal {
    private String user;

    public PrincipalImpl(String var1) {
        this.user = var1;
    }

    public boolean equals(Object var1) {
        if(var1 instanceof PrincipalImpl) {
            PrincipalImpl var2 = (PrincipalImpl)var1;
            return this.user.equals(var2.toString());
        } else {
            return false;
        }
    }

    public String toString() {
        return this.user;
    }

    public int hashCode() {
        return this.user.hashCode();
    }

    public String getName() {
        return this.user;
    }
}
