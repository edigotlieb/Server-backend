/** 
 * FILE : DTDDeleteRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.DTDRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import Statement.Statement;
import java.sql.SQLException;

public class DTDDeleteRequest extends DTDRequest{

    String tableName;
    Statement where;

    public DTDDeleteRequest(String tableName, Statement where, Credentials creds) {
        this(creds);
        this.tableName = tableName;
        this.where = where;
    }
            
    
    public DTDDeleteRequest(Credentials creds) {
        super(creds);
    }

    @Override
    public DTD_ACTION_TYPE getActionType() {
        return DTD_ACTION_TYPE.DELETE;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
