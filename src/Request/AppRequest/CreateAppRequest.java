/** 
 * FILE : CreateAppRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.AppRequest;

import Request.Credentials;
import SQL.SqlExecutor;
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
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException {
        // no such app name yet
        // user is devel
        // check specific app key
        return true;
    }

}
