/** 
 * FILE : UserSignupRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.UserRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import java.sql.SQLException;

public class UserSignupRequest extends UserRequest{

    public UserSignupRequest(Credentials creds) {
        super(creds);
    }

    @Override
    public USER_ACTION_TYPE getActionType() {
        return USER_ACTION_TYPE.SIGN_UP;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
        if(!this.creds.isAnonymous()) {
            throw new ValidationException(10);
        }        
        if(!this.creds.isAdminApp()) {
            throw new ValidationException(6);
        }        
        return true;
    }

}
