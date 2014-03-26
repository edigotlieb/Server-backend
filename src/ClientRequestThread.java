

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;




public class ClientRequestThread extends Thread {

    Connection con;
    Socket socket;
    InputStream is;
    OutputStream os;
    
    public ClientRequestThread(Connection con, Socket socket) {
        this.con = con;
        this.socket = socket;      
    }
    
    @Override
    public void run() {             
        // open a stream
            //this.is = socket.getInputStream();
            //this.os = socket.getOutputStream();
        // send a challenge
        // process response to request
        // validate request
        // execute request
        // send resultSet
    }

}
