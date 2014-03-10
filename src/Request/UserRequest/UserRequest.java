/** 
 * FILE : UserRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.UserRequest;

import Request.Credentials;
import Request.Request;

public abstract class UserRequest extends Request{

    public UserRequest(Credentials creds) {
        super(creds);
    }
    
    public enum USER_ACTION_TYPE {
        SIGN_UP,SIGN_IN,UPDATE,ADD_PERMISSION,SELECT
    }
    
    @Override
    public final TYPE getType() {
        return Request.TYPE.USER;
    }
    
    abstract public USER_ACTION_TYPE getActionType();
}