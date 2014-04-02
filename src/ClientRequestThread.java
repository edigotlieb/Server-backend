
import Request.Exceptions.ValidationException;
import Request.Request;
import Request.RequestFactory;
import SQL.SqlExecutor;
import Utilities.Hashing;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientRequestThread extends Thread {

    Connection con;
    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;

    public ClientRequestThread(Connection con, Socket socket) {
        this.con = con;
        this.socket = socket;
    }

    @Override
    public synchronized void run() {
        try {
            // open a stream
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException ex) {
            this.closeThread();
            return;
        }
        String chal;
        try {
            // send a challenge
            chal = Hashing.generateChallenge();
            this.writer.write(chal);

        } catch (IOException ex) {
            // couldnt send challenge
            this.closeThread();
            return;
        }
        try {
            this.wait((int) RuntimeParams.getParams("ResponseTimeOut"));
            if (!reader.ready()) {
                // client timed-out
                this.closeThread();
                return;
            }
        } catch (InterruptedException ex) {
            // thread interupted ?
            this.closeThread();
            return;
        } catch (IOException ex) {
            // cand check if reader is ready
        }
        Request clientRequest;
        try {
            // process response to request
            clientRequest = RequestFactory.createRequestFromString(reader.readLine());
        } catch (IOException ex) {
            // cant read line from stream
            this.closeThread();
            return;
        }
        
        try {
            // validate request
            clientRequest.Validate(new SqlExecutor(con),chal);
        } catch (SQLException ex) {
            // some bad SQL
        } catch (ValidationException ex) {
            // validation error
            // send back response
        }
                
        // execute request
        // send resultSet
    }

    private void closeThread() {
        try {
            this.reader.close();
            this.writer.close();
            this.socket.close();
            this.con.close();
        } catch (IOException | SQLException ex) {
            // couldnt close stuff
        }
    }

}
