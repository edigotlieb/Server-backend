/** 
 * FILE : RemovePermissionGroupForTableRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.AppRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import java.sql.SQLException;

public class RemovePermissionGroupForTableRequest extends AppRequest{

    Permission toRemove;
    
    public RemovePermissionGroupForTableRequest(Credentials creds) {
        super(creds);
    }

    public RemovePermissionGroupForTableRequest(Permission.PERMISSION_TYPE type, String appName, String tableName, String gorupName, Credentials creds) {
        this(creds);
        this.toRemove = new Permission(type, appName, tableName, gorupName);
    }

    @Override
    public APP_ACTION_TYPE getActionType() {
        return APP_ACTION_TYPE.REMOVE_PERMISSION_GROUP_FOR_TABLE;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
