
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/** 
 * FILE : ThreadHandler.java
 AUTHORS : Erez Gotlieb    
 DESCRIPTION : 
 */ 


public class ThreadHandler {
    
    private static ArrayList<ClientRequestThread> threads;  
    private static ServerSocket ss;
    
    private final static int port = 0;
    private final static int backLog = 1; // ?
    private final static int maxThreads = 2;
    
    // init code
    static {
        threads = new ArrayList<>();
        try {
            ss = new ServerSocket(port, backLog);
        } catch (IOException ex) {
            // cant start the server socket
            // System.exit(1);
        }
    }
    
    public static void run() {
        while(true)  {//?
           try {
                Socket newSocket = ss.accept();
                if(threads.size() == maxThreads) {
                    // give the new socket and error and close it
                    newSocket.close();
                } else {
                    // create a new requestHandler and give it the socket and a new db connection
                    ClientRequestThread newRequestThread = new ClientRequestThread(null, newSocket);
                    threads.add(newRequestThread);
                    newRequestThread.start();
                }
                updateThreads();
           } catch(IOException ex) {
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
