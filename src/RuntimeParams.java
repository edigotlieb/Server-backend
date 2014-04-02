
import java.util.HashMap;

/** 
 * FILE : RuntimeParams.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 


public class RuntimeParams {
    private static final HashMap<String,Object> params = new HashMap<>();
    
    static {
        // default params
        params.put("DriverClass", "");
        params.put("DBUser", "");
        params.put("DBPass", "");        
        params.put("DBURL", "");
        params.put("MaxPoolSize", 0);
        params.put("MaxStatementsPerConnection", 0);
        params.put("MaxIdleTime", 0);
        params.put("RequestPort", 0);
        params.put("MaxThreads", 0);
        params.put("SocketBackLog", 0);
        params.put("ResponseTimeOut", 4000); // time in millis
    }
   
    public static Object getParams(String key) {
        return params.get(key);
    }
    
    public static boolean readParams(String fileName) {
        // read the params from a file
        return true;
    }
    
    
}
