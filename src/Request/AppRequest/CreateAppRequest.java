/** 
 * FILE : CreateAppRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.AppRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import SQL.Utilities.ExistenceValidator;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateAppRequest extends AppRequest {

    String appName;
    
    public CreateAppRequest(Credentials creds,String appName) {
        super(creds);
        this.appName = appName;
    }

    @Override
    public APP_ACTION_TYPE getActionType() {
        return AppRequest.APP_ACTION_TYPE.CREATE_APP;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException,ValidationException {
        // no such app name yet
		if(ExistenceValidator.isAppByName(sqlExc, appName)){
			throw new ValidationException(14);
		}
        // user is devel
		if(!this.creds.isDeveloper()){
			throw new ValidationException(6);
		}
		
        // check specific app_id
        if(!this.creds.isMasterApplication()) {
            throw new ValidationException(13);
        }
        return true;
    }

    @Override
    protected ResultSet performRequest(SqlExecutor sqlExc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
