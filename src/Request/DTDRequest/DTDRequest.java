/** 
 * FILE : DTDRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.DTDRequest;

import Request.Credentials;
import Request.Request;

public abstract class DTDRequest extends Request{
    
    public DTDRequest(Credentials creds) {
        super(creds);
    }
    
    public enum DTD_ACTION_TYPE {
        SELECT,INSERT,UPDATE,DELETE
    }
    
    @Override
    public final Request.TYPE getType() {
        return Request.TYPE.DTD;
    }
    
    
    public abstract DTD_ACTION_TYPE getActionType();
    
    
}
