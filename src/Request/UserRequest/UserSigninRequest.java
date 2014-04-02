/** 
 * FILE : SignInRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.UserRequest;

import Request.Credentials;
import SQL.SqlExecutor;
import java.sql.ResultSet;

public class UserSigninRequest extends UserRequest{
            
    public UserSigninRequest(Credentials creds) {
        super(creds);
    }

    @Override
    public USER_ACTION_TYPE getActionType() {
        return USER_ACTION_TYPE.SIGN_IN;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) {
        return true;
    }

    @Override
    protected ResultSet performRequest(SqlExecutor sqlExc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
