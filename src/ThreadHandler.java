
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * FILE : ThreadHandler.java
 AUTHORS : Erez Gotlieb    
 DESCRIPTION : 
 */ 


public class ThreadHandler {
    
    private static boolean wasInit = false;
    
    private static ArrayList<ClientRequestThread> threads;  
    private static ServerSocket ss;
    
    private static final ComboPooledDataSource ds = new ComboPooledDataSource();
    
    public static boolean init(String paramFileName) {
        
        RuntimeParams.readParams(paramFileName);       // read all the params from a config file
        
        threads = new ArrayList<>();
        
        try {
            
            // init the server socket
            ss = new ServerSocket((int) RuntimeParams.getParams("RequestPort"), (int) RuntimeParams.getParams("SocketBackLog"));
            
            // init the data source
            ds.setDriverClass(String.valueOf(RuntimeParams.getParams("DriverClass")));
            ds.setUser(String.valueOf(RuntimeParams.getParams("DBUser")));
            ds.setPassword(String.valueOf(RuntimeParams.getParams("DBPass")));
            ds.setJdbcUrl(String.valueOf(RuntimeParams.getParams("DBURL")));
            ds.setMaxPoolSize((int) RuntimeParams.getParams("MaxPoolSize"));
            ds.setMaxStatementsPerConnection((int) RuntimeParams.getParams("MaxStatementsPerConnection"));
            ds.setMaxIdleTime((int) RuntimeParams.getParams("MaxIdleTime"));                     
            
            
        } catch (IOException | PropertyVetoException ex) {
            // cant start the server socket or open param file or set params of the DS
            // System.exit(1);
        }
        wasInit = true;
        return true;
    }
  
    
    public static void run() {
        
        if(!wasInit) return; // check we were initiallized
        
        while(true)  {// really want this?
           try {
                Socket newSocket = ss.accept();
                if(threads.size() == (int) RuntimeParams.getParams("MaxThreads")) {
                    // give the new socket an error and close it
                    newSocket.close();
                } else {
                    // create a new requestHandler and give it the socket and a new db connection
                    ClientRequestThread newRequestThread = new ClientRequestThread(ds.getConnection(), newSocket);
                    threads.add(newRequestThread);
                    newRequestThread.start();
                }
                updateThreads();
           } catch(IOException | SQLException ex) {
               // something went wrong
           }          
        }
    }

    private static void updateThreads() {
        for(ClientRequestThread t: threads) {
            if(!t.isAlive()) {
               threads.remove(t);
            }
        }
    }
}
