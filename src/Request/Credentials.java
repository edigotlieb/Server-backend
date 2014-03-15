package Request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author talpiot
 */
public class Credentials {

    public final static String superAdmin = "Super_Admin";
    public final static String developer = "Developer";
    public final static String anonymous = "Anonymous";
    private final String username, hashedPassword;
    private final String hashedAppKey;
    private final String appName;
    //More Information
    private String name, dispName, email;
    private int year, roomNum;
    private List<String> permissions;

    public String[] getPermissions() {
        return this.permissions.toArray(new String[this.permissions.size()]);
    }

    public boolean isAnonymous() {
        return this.username.equals(anonymous);
    }

    public boolean isSuperAdmin() {
        return isInPermissionGroup(superAdmin);
    }

    public boolean isAppSuperAdmin() {
        return isSuperAdmin() || isInPermissionGroup(this.appName + "_" + superAdmin);
    }
    public boolean isDeveloper() {
        return isSuperAdmin() || isInPermissionGroup(this.developer);
    }

    public boolean isInPermissionGroup(String permissionGroupName) {
        if (permissionGroupName.equals(anonymous)) {
            return true;
        }
        return this.permissions.contains(permissionGroupName);
    }

    public void setPermissions(Collection<String> permissions) {
        this.permissions = new ArrayList<>(permissions);
    }

    public String getName() {
        return this.name;
    }

    public String getDispName() {
        return this.dispName;
    }

    public String getEmail() {
        return this.email;
    }

    public int getYear() {
        return this.year;
    }

    public int getRoomNum() {
        return this.roomNum;
    }

    public Credentials(String username, String hashed_password, String app_name, String hashed_app_key) {
        this.username = username;
        this.hashedPassword = hashed_password;
        this.appName = app_name;
        this.hashedAppKey = hashed_app_key;
    }

    public void setMoreInfo(String name, String disp_name, String email, int year, int room_num) {
        this.name = name;
        this.dispName = name;
        this.email = email;
        this.year = year;
        this.roomNum = room_num;
    }

    public String getUsername() {
        return this.username;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public String getHashedAppKey() {
        return this.hashedAppKey;
    }

    public String getAppName() {
        return this.appName;
    }
}
