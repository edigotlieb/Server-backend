
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
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
    
    private static final Logger logger = Logger.getGlobal();
    private static FileHandler fh;

        
    
    public static boolean init(String paramFileName) {
        try {
            RuntimeParams.readParams(paramFileName);       // read all the params from a config file
        } catch (Exception ex) {
            // cant open param file
            // exit
        }
        
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
            
            fh = new FileHandler((String)RuntimeParams.getParams("LogFileName"));
            
        } catch (IOException | PropertyVetoException ex) {
            // cant start the server socket or open param file or set params of the DS
            // or cant open log file
            // System.exit(1);
        }
    
        logger.addHandler(fh);
        logger.setLevel(Level.ALL);
        
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
