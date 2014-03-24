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

    public RemovePermissionGroupForTableRequest(Credentials creds) {
        super(creds);
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
