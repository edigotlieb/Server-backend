
import Request.Exceptions.ErrorMsg;
import Request.Exceptions.ExecutionException;
import Request.Exceptions.RequestException;
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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;

public class ClientRequestThread extends Thread {

    Connection con;
    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;

    final String ERROR_FORMAT = "{'ERROR':'%s'}";
    final String SUCCESS_MSG = "{'ACK':'Request performed'}\n";
    
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
        } catch (JSONException ex) {
            // bad format!
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
        ResultSet resultSet = null;
        try {
            resultSet = clientRequest.execute(new SqlExecutor(con));
            // execute request
        } catch (SQLException ex) {
            // execution went wrong
        } catch (ExecutionException ex) {
            // not validated
        }   
       
        try {
        // send resultSet
        if(resultSet == null) {
            this.writer.write(SUCCESS_MSG);                        
        } else {
            this.writer.write(createResponse(resultSet));
        }        
        this.closeThread();
        } catch(IOException | SQLException ex) {
            // cant write result or read result set
        }
        
    }

    
    private String createResponse(ResultSet rs) throws SQLException {
        JSONObject response = new JSONObject();
        ResultSetMetaData rsmd = rs.getMetaData();
        JSONObject row;
        int rowNum = 1;
        while(rs.next()) {                      
            row = new JSONObject();
            for(int i=1; i <= rsmd.getColumnCount(); i++) {
                row.append(rsmd.getColumnName(i), String.valueOf(rs.getObject(i)));                        
            }
            response.append("Entry"+rowNum, row);
            rowNum++;
        }
            
        return response.toString()+"\n";
    }
    
    //  is this the desired signature?
    private String createErrorResponse(RequestException ex) {
        return String.format(ERROR_FORMAT, ErrorMsg.getErrorMsg(ex))+"\n";
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
