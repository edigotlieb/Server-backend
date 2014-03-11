package Request;



/**
 *
 * @author talpiot
 */
public class Credentials {
    String username,hashed_password;
    String hashed_app_key;
    String app_name;
    
    public Credentials(String username,String hashed_password,String app_name,String hashed_app_key) {
        this.username = username;
        this.hashed_password = hashed_password;
        this.app_name = app_name;
        this.hashed_app_key = hashed_app_key;
    }

    public String getUsername() {
        return this.username;
    }
    
    public String getHashedPassword() {
        return this.hashed_password;
    }
    
    public String getHashedAppKey() {
        return this.hashed_app_key;
    }
    
    public String getAppName() {
        return this.app_name;
    }
    
}
