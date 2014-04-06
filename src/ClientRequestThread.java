
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
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class ClientRequestThread extends Thread {

    Connection con;
    Socket socket;
    static final Logger log = Logger.getGlobal();
    BufferedReader reader;
    BufferedWriter writer;
    PrintWriter out;
    private final int ID;
    private final static String LOG_FORMAT_MSG = "CRT-%d: %s";

    final String ERROR_FORMAT = "{'ERROR':'%s'}";
    final String SUCCESS_MSG = "{'ACK':'Request performed'}\n";

    public ClientRequestThread(Connection con, Socket socket, int ID) {
        this.con = con;
        this.socket = socket;
        this.ID = ID;
    }

    public void logMSG(String msg, Level lvl) {
        log.log(lvl, String.format(LOG_FORMAT_MSG, ID, msg));
    }

    public void logException(Exception ex, Level lvl) {
        log.log(lvl, String.format(LOG_FORMAT_MSG, ID, ex.getMessage()), ex);
    }

    @Override
    public synchronized void run() {

//<editor-fold defaultstate="collapsed" desc="open streams">
        try {
            logMSG("opening streams...", Level.INFO);
            // open a stream
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream());
        } catch (IOException ex) {
            logMSG("failed to open streams...", Level.INFO);
            logException(ex, Level.INFO);
            this.closeThread();
            return;
        }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="send challenge">
        String chal;

        // send a challenge
        chal = Hashing.generateChallenge();
        this.out.println(chal);
        this.out.flush();
        logMSG(String.format("sending challenge to client - %s", chal), Level.INFO);

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="wait for client response">
        try {
            int time = (int) RuntimeParams.getParams("ResponseTimeOut");
            logMSG(String.format("waiting for client request %d millis...", time), Level.INFO);
            Thread.sleep(time);
            logMSG("done sleeping...", Level.INFO);
            if (!reader.ready()) {
                // client timed-out
                logMSG("client timed out...", Level.INFO);
                this.closeThread();
                return;
            }

        } catch (InterruptedException ex) {
            // thread interupted ?
            logMSG("InterruptedException...", Level.INFO);
            logException(ex, Level.INFO);
            this.closeThread();
            return;
        } catch (IOException ex) {
            logMSG("failed to read stream...", Level.INFO);
            logException(ex, Level.INFO);
            // cand check if reader is ready
        }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="proccess client request">
        Request clientRequest;
        try {
            int maxLength = (int) RuntimeParams.getParams("BufferSize");
            char[] buffer = new char[maxLength];
            int length = reader.read(buffer);
            if (length == maxLength) {
                // some error
                this.out.print(this.createErrorResponse(501));
                this.out.flush();

                this.closeThread();
                return;
            }
            // logMSG("REQUEST: "+(new String(buffer)),Level.INFO);
            // process response to request
            clientRequest = RequestFactory.createRequestFromString(new String(buffer));
            this.logMSG("proc client request...", Level.INFO);
        } catch (IOException ex) {
            // cant read line from stream
            logMSG("failed to read request from stream...", Level.INFO);
            logException(ex, Level.INFO);
            this.closeThread();
            return;
        } catch (JSONException ex) {
            // bad format! - send bad format error
            this.out.print(this.createErrorResponse(100));
            this.out.flush();
            logMSG("request format error...", Level.INFO);
            logException(ex, Level.INFO);
            this.closeThread();
            return;

        }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="validate client request">
        try {
            // validate request
            clientRequest.Validate(new SqlExecutor(con), chal);
        } catch (SQLException ex) {
            // some bad SQL
            logMSG("validation SQL error...", Level.SEVERE);
            logException(ex, Level.SEVERE);

            this.out.print(this.createErrorResponse(500));
            this.out.flush();

            this.closeThread();
            return;
        } catch (ValidationException ex) {
            // validation error
            // send back response
            logMSG("client request denied with error code " + ex.getErrorCode(), Level.INFO);
                // logException(ex, Level.INFO);

            // System.out.print(this.createErrorResponse(ex.getErrorCode()));                
            this.out.print(this.createErrorResponse(ex.getErrorCode()));
            this.out.flush();

            this.closeThread();
            return;
        }
        logMSG("Client request validated", Level.INFO);
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="exe client request">
        ResultSet resultSet = null;
        try {
            resultSet = clientRequest.execute(new SqlExecutor(con));
            // execute request            
        } catch (SQLException ex) {
            // execution went wrong
            this.logMSG("general execution error...", Level.INFO);
            this.logException(ex, Level.INFO);
            this.out.printf(this.createErrorResponse(52));
            this.out.flush();
            this.closeThread();
            return;

        } catch (ExecutionException ex) {
            // not validated
            this.logMSG(ErrorMsg.getErrorMsg(51), Level.INFO);
        }
        logMSG("Client request executed", Level.INFO);
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="response for client">
        try {
            // send resultSet
            if (resultSet == null) {
                //this.writer.write(SUCCESS_MSG);
                this.out.print(SUCCESS_MSG);

            } else {
                //this.writer.write(createResponse(resultSet));
                this.out.print(createResponse(resultSet));
            }
            //this.writer.flush();
            this.out.flush();

        } catch (SQLException ex) {
            // cant write result or read result set
        }
        logMSG("Response sent to client", Level.INFO);
        this.closeThread();
//</editor-fold>

    }

    private synchronized String createResponse(ResultSet rs) throws SQLException {
        JSONObject response = new JSONObject();
        ResultSetMetaData rsmd = rs.getMetaData();
        JSONObject row;
        int rowNum = 1;
        while (rs.next()) {
            row = new JSONObject();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                row.append(rsmd.getColumnName(i), String.valueOf(rs.getObject(i)));
            }
            response.append("Entry" + rowNum, row);
            rowNum++;
        }

        return response.toString() + "\n";
    }

    //  is this the desired signature?
    private synchronized String createErrorResponse(String msg) {
        return String.format(ERROR_FORMAT, msg) + "\n";
    }

    //  is this the desired signature?
    private synchronized String createErrorResponse(int errorCode) {
        return createErrorResponse(ErrorMsg.getErrorMsg(errorCode));
    }

    private void closeThread() {
        logMSG("closing request thread...", Level.INFO);
        try {
            //       this.reader.close();
            //        this.writer.close();
            this.socket.close();
            this.con.close();
            log.getHandlers()[0].flush();
        } catch (IOException | SQLException ex) {
            // couldnt close stuff
            logMSG("failed at closing request thread...", Level.INFO);
            logException(ex, Level.INFO);
        }
    }

}
