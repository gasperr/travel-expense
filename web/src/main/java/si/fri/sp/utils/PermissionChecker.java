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
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.naming.NoPermissionException;

import java.io.Serializable;
import java.security.Principal;
import java.security.acl.Acl;
import java.security.acl.NotOwnerException;

/**
 * @Author Gasper Andrejc, created on 10/jan/2016
 */

@SessionScoped
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
    public boolean hasUserPermission() throws NoPermissionException {
        Principal testRole = new PrincipalImpl(getCurrentUser().getType().getStringValue());
        return accessList.checkPermission(testRole, userPermission);
    }

    /**
     * @return true if user is Finance member
     */
    public boolean hasFinancePermission() throws NoPermissionException {
        Principal testRole = new PrincipalImpl(getCurrentUser().getType().getStringValue());
        return accessList.checkPermission(testRole, financePermission);
    }

    /**
     * @return true if user is Managment member
     */
    public boolean hasManagmentPermission() throws NoPermissionException {
        Principal testRole = new PrincipalImpl(getCurrentUser().getType().getStringValue());
        return accessList.checkPermission(testRole, managmentPermission);
    }

    /**
     * returns dashboard url for user
     * @return
     */
    public String getDefaultUrl() throws NoPermissionException{
        String role = "";
        if(hasUserPermission()){
            role = "user";
        }else if(hasFinancePermission()){
            role = "finance";
        }else if(hasManagmentPermission()){
            role = "managment";
        }

        return "/views/"+role+"/dashboard.xhtml";
    }


    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() throws NoPermissionException {
        if(currentUser != null){
            return currentUser;
        }else{
            throw new NoPermissionException("No user can be found in session...");
        }
    }
}
