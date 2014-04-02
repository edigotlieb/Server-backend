/** 
 * FILE : UserUpdateUserPasswordRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.UserRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import java.sql.SQLException;

public class UserUpdateUserPasswordRequest extends UserRequest{

    String userToUpdate,newPass;
    
    public UserUpdateUserPasswordRequest(Credentials creds) {
        super(creds);
    }

    public UserUpdateUserPasswordRequest(Credentials creds, String userToUpdate,String newPass) {
        this(creds);
        this.userToUpdate = userToUpdate;
        this.newPass = newPass;
    }
    
    @Override
    public USER_ACTION_TYPE getActionType() {
        return USER_ACTION_TYPE.UPDATE_PASSWORD;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
        if (this.creds.getUsername().equals(this.userToUpdate)) {
            throw new ValidationException(6);
        }
        return true;
    }

}
