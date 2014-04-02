/** 
 * FILE : RemovePermissionGroupForTableRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.AppRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import SQL.Utilities.ExistenceValidator;
import java.sql.SQLException;

public class RemovePermissionGroupForTableRequest extends AppRequest{

    Permission toRemove;
    
    public RemovePermissionGroupForTableRequest(Permission.PERMISSION_TYPE type, String appName, String tableName, String gorupName, Credentials creds) {
        super(creds);
        this.toRemove = new Permission(type, appName, tableName, gorupName);
    }

    @Override
    public APP_ACTION_TYPE getActionType() {
        return APP_ACTION_TYPE.REMOVE_PERMISSION_GROUP_FOR_TABLE;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
        //check appName exists
		if(!ExistenceValidator.isAppByName(sqlExc, this.toRemove.getAppName())){
			throw new ValidationException(1);
		}
		
		//check table name exists
		if(!ExistenceValidator.isTableByName(sqlExc, this.toRemove.getTableName())){
			throw new ValidationException(12);
		}
		//check permission group exists
		if(!ExistenceValidator.isPermissionGroupByName(sqlExc, this.toRemove.getPermissionGroup())){
			throw new ValidationException(11);
		}
		
        if(!this.creds.isAdminApp()){
			throw new ValidationException(6);
		}
		return true;
    }
    
}
