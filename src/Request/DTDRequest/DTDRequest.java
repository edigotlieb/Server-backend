/** 
 * FILE : DTDRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.DTDRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import Request.Request;
import SQL.SqlExecutor;
import java.sql.SQLException;

public abstract class DTDRequest extends Request{
    protected String tableName;
    public DTDRequest(Credentials creds, String tableName) {
        super(creds);
		this.tableName = tableName;
    }
    
    public enum DTD_ACTION_TYPE {
        SELECT,INSERT,UPDATE,DELETE
    }
    
    @Override
    public final Request.TYPE getType() {
        return Request.TYPE.DTD;
    }
    
    
    public abstract DTD_ACTION_TYPE getActionType();
    
    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
		if(!SQL.Utilities.Utils.isAlphaNumeric(tableName)){
			throw new ValidationException(15);
		}
		if(!this.creds.getTablePermissionList(sqlExc, this.tableName).contains(this.getActionType())){
			throw new ValidationException(6);
		}
		return true;
    }
}
