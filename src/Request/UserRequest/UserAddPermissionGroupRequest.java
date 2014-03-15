/** 
 * FILE : UserAddPermissionGroupRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.UserRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import java.sql.SQLException;

public class UserAddPermissionGroupRequest extends UserRequest{

    public UserAddPermissionGroupRequest(Credentials creds) {
        super(creds);
    }

    @Override
    public USER_ACTION_TYPE getActionType() {
        return USER_ACTION_TYPE.ADD_PERMISSION;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
        // check user and group exists
        // check if adder is super admin or group admin
        return true;
    }

}
