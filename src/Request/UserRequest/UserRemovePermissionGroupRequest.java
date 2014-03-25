/** 
 * FILE : UserRemovePermissionGroupRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.UserRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import java.sql.SQLException;

public class UserRemovePermissionGroupRequest extends UserRequest{

    String userToRemoveFrom,groupToRemove;

    public UserRemovePermissionGroupRequest(String userToRemoveFrom, String groupToRemove, Credentials creds) {
        this(creds);
        this.userToRemoveFrom = userToRemoveFrom;
        this.groupToRemove = groupToRemove;
    }
    
    public UserRemovePermissionGroupRequest(Credentials creds) {
        super(creds);
    }

    @Override
    public USER_ACTION_TYPE getActionType() {
        return USER_ACTION_TYPE.REMOVE_PERMISSION;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
