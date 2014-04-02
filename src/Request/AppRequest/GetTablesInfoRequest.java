/** 
 * FILE : GetTablesInfo.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.AppRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GetTablesInfoRequest extends AppRequest{


    public GetTablesInfoRequest(Credentials creds) {
        super(creds);
    }

    	@Override
	public APP_ACTION_TYPE getActionType() {
		return AppRequest.APP_ACTION_TYPE.GET_TABLES_INFO;
	}

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
        if(!this.creds.isAppSuperAdmin()) {
            throw new ValidationException(6);            
        }
        return true;
    }

    @Override
    protected ResultSet performRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
     
}
