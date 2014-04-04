
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
import java.util.logging.SimpleFormatter;

/** 
 * FILE : ThreadHandler.java
 AUTHORS : Erez Gotlieb    
 DESCRIPTION : 
 */ 


public class ThreadHandler {
    
    private static boolean wasInit = false;
    
    private static ArrayList<ClientRequestThread> threads;  
    private static ServerSocket ss;
    
    private static  ComboPooledDataSource ds;
    
    private static final Logger logger = Logger.getGlobal();
    private static FileHandler fh;

        
    
    public static boolean init(String paramFileName) {
        try {
            RuntimeParams.readParams(paramFileName);       // read all the params from a config file
        } catch (Exception ex) {
            // cant open param file
            logger.log(Level.SEVERE, "Can't open parameter file");
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            logger.log(Level.SEVERE, "closing...");
            System.exit(0);
            // exit
        }
        
        threads = new ArrayList<>();
        
        try {
            
            // init the server socket
            ss = new ServerSocket((int) RuntimeParams.getParams("RequestPort"), (int) RuntimeParams.getParams("SocketBackLog"));
            
            ds = new ComboPooledDataSource();
            // init the data source
            ds.setDriverClass(String.valueOf(RuntimeParams.getParams("DriverClass")));
            ds.setUser(String.valueOf(RuntimeParams.getParams("DBUser")));
            ds.setPassword(String.valueOf(RuntimeParams.getParams("DBPass")));
            ds.setJdbcUrl(String.valueOf(RuntimeParams.getParams("DBURL")));
            ds.setMaxPoolSize((int) RuntimeParams.getParams("MaxPoolSize"));
            ds.setMaxStatementsPerConnection((int) RuntimeParams.getParams("MaxStatementsPerConnection"));
            ds.setMaxIdleTime((int) RuntimeParams.getParams("MaxIdleTime"));                     
                             
            ds.getConnection();
            
            fh = new FileHandler((String)RuntimeParams.getParams("LogFileName"));
            fh.setFormatter(new SimpleFormatter());
            
        } catch (IOException | PropertyVetoException | SQLException ex) {
            // cant start the server socket or open param file or set params of the DS
            // or cant open log file
            logger.log(Level.SEVERE, "Failed on port or DB init");
            logger.log(Level.SEVERE, ex.getMessage(), ex);       
            logger.log(Level.SEVERE, "closing...");
             System.exit(1);
        } 
    
        logger.addHandler(fh);
        logger.setLevel(Level.ALL);
        
        logger.log(Level.INFO, "finished initiallizing...");
        wasInit = true;
        return true;
    }
  
    
    public static void run() {
        
        if(!wasInit) return; // check we were initiallized
        int IDs = 0;
        while(true)  {// really want this?
           try {
                logger.log(Level.INFO, "waiting for incoming connection...");
                Socket newSocket = ss.accept();
                logger.log(Level.INFO, "accepted new incoming connection...");
                if(threads.size() == (int) RuntimeParams.getParams("MaxThreads")) {
                    // give the new socket an error and close it
                    logger.log(Level.INFO, "refusing new incoming connection because of overhead");
                    newSocket.close();
                } else {
                    // create a new requestHandler and give it the socket and a new db connection
                    logger.log(Level.INFO, "starting new DB connection...");
                    ClientRequestThread newRequestThread = new ClientRequestThread(ds.getConnection(), newSocket,IDs);
                    threads.add(newRequestThread);
                    logger.log(Level.INFO, "transfering control and starting new client request thread...");
                    newRequestThread.start();
                }
                
                logger.log(Level.INFO, "updating thread arrays");
                updateThreads();                
                logger.log(Level.INFO, threads.size()+" Threads active.");
                logger.getHandlers()[0].flush();
                
           } catch(IOException ex) {
               // something went very wrong
               logger.log(Level.SEVERE, "failed to close socket");
               logger.log(Level.SEVERE, ex.getMessage(), ex);   
           }  catch (SQLException ex) {
               logger.log(Level.SEVERE, "failed to open db connection");
               logger.log(Level.SEVERE, ex.getMessage(), ex);   
               logger.log(Level.SEVERE, "closing...");
               System.exit(1);
           } 
           IDs++;
        }
    }

    private synchronized static void updateThreads() {
        for(int i=0; i< threads.size(); i++) {
            if(!threads.get(i).isAlive()) {                
               threads.remove(i);
            }
        }
    }
}
