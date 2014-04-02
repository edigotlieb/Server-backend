/** 
 * FILE : DTDInsertRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.DTDRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DTDInsertRequest extends DTDRequest{

    Map<String,String> data;

    public DTDInsertRequest(String tableName, Map<String, String> data, Credentials creds) {
        super(creds, tableName);
        this.data = data;
    }        

    @Override
    public DTD_ACTION_TYPE getActionType() {
        return DTD_ACTION_TYPE.INSERT;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ResultSet performRequest(SqlExecutor sqlExc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
