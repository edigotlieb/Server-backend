/** 
 * FILE : ErrorMsg.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.Exceptions;

import java.util.HashMap;

public class ErrorMsg {

    
    private static final HashMap<Integer,String> msgs;
    
    static {
        msgs = new HashMap<>();
        msgs.put(1, "Bad app name");
        msgs.put(2, "Bad app key");
        msgs.put(3, "Bad username");
        msgs.put(4, "Bad user password");
        msgs.put(5, "Permission-group name already exists");
        msgs.put(6, "User has insufficient permissions to perform the desired operations");        
        msgs.put(7, "Table name already exists");
        msgs.put(8, "No such table exists");
        msgs.put(9, "No such permission-group exists");
        msgs.put(10, "Must be anon for signup");
    }
    
    
    private static String getErrorMsg(int errorCode) {
        return msgs.get(errorCode);
    }
    
    public static String getErrorMsg(RequestException ex){
        return getErrorMsg(ex.getErrorCode());
    }
    
    
    
    
    
    
}
