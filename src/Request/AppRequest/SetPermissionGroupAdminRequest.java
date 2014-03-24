/** 
 * FILE : SetPermissionGroupAdminRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.AppRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import java.sql.SQLException;

public class SetPermissionGroupAdminRequest extends AppRequest{

    public SetPermissionGroupAdminRequest(Credentials creds) {
        super(creds);
    }

    @Override
    public APP_ACTION_TYPE getActionType() {
        return APP_ACTION_TYPE.SET_PERMISSIONGROUP_ADMIN;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
        // check if the user is the current permission group admin
        return false;
    }

}
