/** 
 * FILE : PreparedStatementStringas.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package SQL.PreparedStatements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreparedStatementStrings {

    private static final HashMap<String,String> fileNames = new HashMap<>();
    
    static {
        fileNames.put("getAllUserInfo", "getUser.sql");
        fileNames.put("getAllAppInfo", "getApp.sql");
    }
    
    private final HashMap<String,String> PreparedSql = new HashMap<>();
    
    private static PreparedStatementStrings instance = null;
    
    public static PreparedStatementStrings getInstance() {
         if(instance != null) {
             return instance;
         } else {
             return (instance = new PreparedStatementStrings());
         }
    }    
    
    private PreparedStatementStrings() {
        readFiles();
    }

    private void readFiles() {
        for(String key : fileNames.keySet()) {
            try {
                PreparedSql.put(key, new String(Files.readAllBytes(Paths.get("preparedSQL/"+fileNames.get(key)))));
            } catch (IOException ex) {
                Logger.getLogger(PreparedStatementStrings.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String getSQL(String key) {
        return this.PreparedSql.get(key);
    }
    
    
    
}
