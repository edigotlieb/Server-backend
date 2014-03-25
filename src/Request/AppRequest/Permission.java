/**
 * FILE : Permission.java
 * AUTHORS : Erez Gotlieb
 * DESCRIPTION :
 */
package Request.AppRequest;

public class Permission {

    public enum PERMISSION_TYPE {

        SELECT, INSERT, UPDATE, DELETE
    }

    private PERMISSION_TYPE type;
    private String appName, tableName, permissionGroup;

    public Permission(PERMISSION_TYPE type, String appName, String tableName, String permissionGroup) {
        this.type = type;
        this.appName = appName;
        this.tableName = tableName;
        this.permissionGroup = permissionGroup;
    }
}
