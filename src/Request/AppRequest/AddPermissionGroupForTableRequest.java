/** 
 * FILE : AddPermissionGroupForTableRequest.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package Request.AppRequest;


import Request.AppRequest.Permission.PERMISSION_TYPE;
import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import SQL.Utilities.ExistenceValidator;
import java.sql.SQLException;

public class AddPermissionGroupForTableRequest extends AppRequest{

    
    Permission permissionToAdd;

    public AddPermissionGroupForTableRequest(PERMISSION_TYPE type, String appName, String tableName, String permissionGroup, Credentials creds) {
        this(creds);
        this.permissionToAdd = new Permission(type, appName, tableName, permissionGroup);
    }
    
    
    
    public AddPermissionGroupForTableRequest(Credentials creds) {
        super(creds);
    }

    @Override
    public APP_ACTION_TYPE getActionType() {
        return APP_ACTION_TYPE.ADD_PERMISSION_GROUP_FOR_TABLE;
    }

    @Override
    protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
		//check appName exists
		if(!ExistenceValidator.isAppByName(sqlExc, this.permissionToAdd.getAppName())){
			throw new ValidationException(1);
		}
		
		//check table name exists
		if(!ExistenceValidator.isTableByName(sqlExc, this.permissionToAdd.getTableName())){
			throw new ValidationException(12);
		}
		//check permission group exists
		if(!ExistenceValidator.isPermissionGroupByName(sqlExc, this.permissionToAdd.getPermissionGroup())){
			throw new ValidationException(11);
		}
		
		if(!this.creds.isMasterApplication()){
			throw new ValidationException(13);
		}
		
        if(!this.creds.isAdminApp()){
			throw new ValidationException(6);
		}
		return true;
    }

}
