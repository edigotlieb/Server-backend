/** 
 * FILE : DTDSelectRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.DTDRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import Statement.Statement;
import java.sql.SQLException;

public class DTDSelectRequest extends DTDRequest{

    Statement where;

    public DTDSelectRequest(String tableName, Statement where, Credentials creds) {
        super(creds, tableName);
        this.where = where;
    }

    @Override
    public DTD_ACTION_TYPE getActionType() {
        return DTD_ACTION_TYPE.SELECT;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
