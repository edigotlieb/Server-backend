
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

/**
 * FILE : RuntimeParams.java AUTHORS : Erez Gotlieb DESCRIPTION :
 */
public class RuntimeParams {

    private static HashMap<String, Object> params = new HashMap<>();

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
        params.put("LogFileName", "logger.txt"); // time in millis
    }

    public static Object getParams(String key) {
        return params.get(key);
    }

    public static boolean readParams(String fileName) throws Exception {
        
        File f = new File(fileName);
        if(!f.exists()) {
            return false;
        }
        params = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        
        JSONObject jsonFormat = new JSONObject(sb.toString().replaceAll("[\\r\\n]", ""));
        String key;
        Iterator<String> keyIterator = jsonFormat.keys();
        while(keyIterator.hasNext()) {
            key = keyIterator.next();
            params.put(key, jsonFormat.get(key));
        }
        
        return true;
    }
}
