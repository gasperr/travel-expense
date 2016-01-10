package si.fri.sp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.fri.sp.entities.User;
import si.fri.sp.entities.enums.UserType;
import si.fri.sp.utils.aclImpl.AclEntryImpl;
import si.fri.sp.utils.aclImpl.AclImpl;
import si.fri.sp.utils.aclImpl.PermissionImpl;
import si.fri.sp.utils.aclImpl.PrincipalImpl;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import java.io.Serializable;
import java.security.Principal;
import java.security.acl.Acl;
import java.security.acl.NotOwnerException;

/**
 * @Author Gasper Andrejc, created on 10/jan/2016
 */

@RequestScoped
@Named
public class PermissionChecker implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionChecker.class);

    private Acl accessList;

    private PermissionImpl financePermission;
    private PermissionImpl managmentPermission;
    private PermissionImpl userPermission;

    private User currentUser;


    @PostConstruct
    private void beanInit() {
        currentUser = new User();
        currentUser.setId(1);
        //todo: change to be read from cookie
        currentUser.setType(UserType.MANAGER);

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

    /**
     * @return true if user is Driver
     */
    public boolean hasUserPermission() {
        Principal testRole = new PrincipalImpl(currentUser.getType().getStringValue());
        return accessList.checkPermission(testRole, userPermission);
    }

    /**
     * @return true if user is Finance member
     */
    public boolean hasFinancePermission() {
        Principal testRole = new PrincipalImpl(currentUser.getType().getStringValue());
        return accessList.checkPermission(testRole, financePermission);
    }

    /**
     * @return true if user is Managment member
     */
    public boolean hasManagmentPermission() {
        Principal testRole = new PrincipalImpl(currentUser.getType().getStringValue());
        return accessList.checkPermission(testRole, managmentPermission);
    }
}
