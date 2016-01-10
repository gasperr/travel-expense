package si.fri.sp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.fri.sp.entities.User;
import si.fri.sp.utils.aclImpl.AclEntryImpl;
import si.fri.sp.utils.aclImpl.AclImpl;
import si.fri.sp.utils.aclImpl.PermissionImpl;
import si.fri.sp.utils.aclImpl.PrincipalImpl;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.security.Principal;
import java.security.acl.Acl;
import java.security.acl.NotOwnerException;

/**
 * @Author Gasper Andrejc, created on 10/jan/2016
 */

@ApplicationScoped
public class PermissionChecker implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionChecker.class);

    private Acl accessList;

    private PermissionImpl financePermission;
    private PermissionImpl managmentPermission;
    private PermissionImpl userPermission;


    @PostConstruct
    private void beanInit() {
        Principal owner = new PrincipalImpl("owner");
        accessList = new AclImpl(owner, "aclList");

        Principal financeUser = new PrincipalImpl("Računovodstvo");
        financePermission = new PermissionImpl("financePermission");
        AclEntryImpl financeEntry = new AclEntryImpl(financeUser);
        financeEntry.addPermission(financePermission);

        Principal managmentUser = new PrincipalImpl("Managment");
        managmentPermission = new PermissionImpl("managmentPermission");
        AclEntryImpl managmentEntry = new AclEntryImpl(managmentUser);
        managmentEntry.addPermission(managmentPermission);

        Principal userUser = new PrincipalImpl("Voznik");
        userPermission = new PermissionImpl("userPermission");
        AclEntryImpl userEntry = new AclEntryImpl(userUser);
        userEntry.addPermission(userPermission);

        try {
            accessList.addEntry(owner, financeEntry);
            accessList.addEntry(owner, managmentEntry);
            accessList.addEntry(owner, userEntry);
        } catch (NotOwnerException e) {
            LOGGER.error("Error while initializing permissions", e);
        }
    }

    public boolean hasUserPermission(User user) {
        Principal testRole = new PrincipalImpl(user.getType().getStringValue());
        return accessList.checkPermission(testRole, userPermission);
    }

    public boolean hasFinancePermission(User user) {
        Principal testRole = new PrincipalImpl(user.getType().getStringValue());
        return accessList.checkPermission(testRole, financePermission);
    }

    public boolean hasManagmentPermission(User user) {
        Principal testRole = new PrincipalImpl(user.getType().getStringValue());
        return accessList.checkPermission(testRole, managmentPermission);
    }
}
