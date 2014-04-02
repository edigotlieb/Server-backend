/** 
 * FILE : UserSelectRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.UserRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import Statement.Statement;
import java.sql.SQLException;

public class UserSelectRequest extends UserRequest{

    Statement where;

    public UserSelectRequest(Statement where, Credentials creds) {
        this(creds);
        this.where = where;
    }    
    
    public UserSelectRequest(Credentials creds) {
        super(creds);
    }

    @Override
    public USER_ACTION_TYPE getActionType() {
        return USER_ACTION_TYPE.SELECT;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
        return true;
    }

}