
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JSONObject jObj = new JSONObject("{ \"RequesterCredentials\": { \"appName\":\"testApp\" , \"appKey\":\"testKey\" , \"username\":\"testUsername\", \"password\":\"testPassword\" } , \"RequestInfo\": { \"requestType\":\"testType\" , \"requestAction\":\"testAction\" } , \"RequestData\": {} }");
        JSONObject creds = jObj.getJSONObject("RequesterCredentials");        
        System.out.println(creds.getString("password"));
    }
    
}
