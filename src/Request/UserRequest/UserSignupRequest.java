/** 
 * FILE : UserSignupRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.UserRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSignupRequest extends UserRequest{

    String user,pass,name,disp_name,email;
    int room,year;

    public UserSignupRequest(String user, String pass, String name, String disp_name, String email, int room, int year, Credentials creds) {
        this(creds);
        this.user = user;
        this.pass = pass;
        this.name = name;
        this.disp_name = disp_name;
        this.email = email;
        this.room = room;
        this.year = year;
    }            
    
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
		//check specific appkey
		if(!this.creds.isMasterApplication()){
			throw new ValidationException(13);
		}
        return true;
    }

    @Override
    protected ResultSet performRequest(SqlExecutor sqlExc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
