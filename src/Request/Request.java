package Request;

import SQL.PreparedStatements.StatementPreparer;
import SQL.SqlExecutor;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 *
 * @author talpiot
 */
public abstract class Request {           
    
    protected final Credentials creds;
    
    public Request(Credentials creds) {
        this.creds = creds;
    }
    
    public enum TYPE {
       USER,APP,DTD
    }
    
   // abstract public Credentials getCreds();
   abstract public TYPE getType();
   
   public final boolean Validate(SqlExecutor sqlExc,String challenge) throws SQLException {
       return false;
   }
   
   protected abstract boolean CheckPermissions(SqlExecutor sqlExc); 
   
}
