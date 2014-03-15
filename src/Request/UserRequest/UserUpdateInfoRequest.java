/** 
 * FILE : UserUpdateInfoRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.UserRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import java.sql.SQLException;

public class UserUpdateInfoRequest extends UserRequest {

    public UserUpdateInfoRequest(Credentials creds) {
        super(creds);
    }

    @Override
    public USER_ACTION_TYPE getActionType() {
        return USER_ACTION_TYPE.UPDATE_INFO;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
        if(!this.creds.isAdminApp()) {
            throw new ValidationException(6);
        }
        return true;
    }
    

}
