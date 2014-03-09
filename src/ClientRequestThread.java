
import java.net.Socket;
import java.sql.Connection;



public class ClientRequestThread implements Runnable{

    Connection con;
    Socket socket;
    
    public ClientRequestThread(Connection con, Socket socket) {
        this.con = con;
        this.socket = socket;
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

}
