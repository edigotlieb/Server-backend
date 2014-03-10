/** 
 * FILE : SignInRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.UserRequest;

import Request.Credentials;

public class SignInRequest extends UserRequest{
            
    public SignInRequest(Credentials creds) {
        super(creds);
    }

    @Override
    public USER_ACTION_TYPE getActionType() {
        return USER_ACTION_TYPE.SIGN_IN;
    }

    @Override
    public Credentials getCreds() {
        return super.creds;
    }

}
