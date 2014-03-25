/** 
 * FILE : DTDUpdateRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.DTDRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import Statement.Statement;
import java.sql.SQLException;
import java.util.Map;

public class DTDUpdateRequest extends DTDRequest{

    String tableName;
    Statement where;
    Map<String,String> data;

    public DTDUpdateRequest(String tableName, Statement where, Map<String, String> data, Credentials creds) {
        this(creds);
        this.tableName = tableName;
        this.where = where;
        this.data = data;
    }        
    
    public DTDUpdateRequest(Credentials creds) {
        super(creds);
    }

    @Override
    public DTD_ACTION_TYPE getActionType() {
        return DTD_ACTION_TYPE.UPDATE;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
