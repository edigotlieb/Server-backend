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

    String userToChange, newName, newDispName, newEmail;
    int newYear, newRoom;

    public UserUpdateInfoRequest(String userToChange, String newName, String newDispName, String newEmail, int newYear, int newRoom, Credentials creds) {
        this(creds);
        this.userToChange = userToChange;
        this.newName = newName;
        this.newDispName = newDispName;
        this.newEmail = newEmail;
        this.newYear = newYear;
        this.newRoom = newRoom;
    }

    public UserUpdateInfoRequest(Credentials creds) {
        super(creds);
    }

    @Override
    public USER_ACTION_TYPE getActionType() {
        return USER_ACTION_TYPE.UPDATE_INFO;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
        if (!this.creds.getUsername().equals(this.userToChange)) {
            throw new ValidationException(6);
        }
        return true;
    }

}
