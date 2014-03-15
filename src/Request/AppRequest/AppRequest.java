/** 
 * FILE : AppRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.AppRequest;

import Request.Credentials;
import Request.Request;

public abstract class AppRequest extends Request{

    public AppRequest(Credentials creds) {
        super(creds);
    }
    
    public enum APP_ACTION_TYPE {
        ADD_TABLE,DROP_TABLE,DELETE_APP,ADD_PERMISSIONGROUP,REMOVE_PERMISSIONGROUP,CREATE_APP,GET_TABLES_INFO
    }
    
    @Override
    public final TYPE getType() {
        return Request.TYPE.APP;
    }
    
    
    public abstract APP_ACTION_TYPE getActionType();

}
