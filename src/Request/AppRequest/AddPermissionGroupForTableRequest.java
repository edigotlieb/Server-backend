/** 
 * FILE : AddPermissionGroupForTableRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.AppRequest;


import Request.AppRequest.Permission.PERMISSION_TYPE;
import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import java.sql.SQLException;

public class AddPermissionGroupForTableRequest extends AppRequest{

    
    Permission permissionToAdd;

    public AddPermissionGroupForTableRequest(PERMISSION_TYPE type, String appName, String tableName, String permissionGroup, Credentials creds) {
        this(creds);
        this.permissionToAdd = new Permission(type, appName, tableName, permissionGroup);
    }
    
    
    
    public AddPermissionGroupForTableRequest(Credentials creds) {
        super(creds);
    }

    @Override
    public APP_ACTION_TYPE getActionType() {
        return APP_ACTION_TYPE.ADD_PERMISSION_GROUP_FOR_TABLE;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
