package Request;



/**
 *
 * @author talpiot
 */
public class Credentials {
    String username,hashed_password;
    String hashed_app_key;
    int app_id;
    
    public Credentials(String username,String hashed_password,int app_id,String hashed_app_key) {
        this.username = username;
        this.hashed_password = hashed_password;
        this.app_id = app_id;
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
    
    public int getAppID() {
        return this.app_id;
    }
    
}
