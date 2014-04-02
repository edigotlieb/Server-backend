/**
 * FILE : AddPermissionGroupForTableRequest.java AUTHORS : Erez Gotlieb
 * DESCRIPTION :
 */
package Request.AppRequest;

import Request.AppRequest.Permission.PERMISSION_TYPE;
import Request.Credentials;
import Request.Exceptions.ExecutionException;
import Request.Exceptions.ValidationException;
import SQL.PreparedStatements.StatementPreparer;
import SQL.SqlExecutor;
import SQL.Utilities.ExistenceValidator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddPermissionGroupForTableRequest extends AppRequest {

	Permission permissionToAdd;

	public AddPermissionGroupForTableRequest(PERMISSION_TYPE type, String appName, String tableName, String permissionGroup, Credentials creds) {
		super(creds);
		this.permissionToAdd = new Permission(type, appName, tableName, permissionGroup);
	}

	@Override
	public APP_ACTION_TYPE getActionType() {
		return APP_ACTION_TYPE.ADD_PERMISSION_GROUP_FOR_TABLE;
	}

	@Override
	protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
		//check appName exists
		if (!ExistenceValidator.isAppByName(sqlExc, this.permissionToAdd.getAppName())) {
			throw new ValidationException(1);
		}

		//check table name exists
		if (!ExistenceValidator.isTableByName(sqlExc, this.permissionToAdd.getTableName())) {
			throw new ValidationException(12);
		}
		//check permission group exists
		if (!ExistenceValidator.isPermissionGroupByName(sqlExc, this.permissionToAdd.getPermissionGroup())) {
			throw new ValidationException(11);
		}

		if (!this.creds.isAdminApp()) {
			throw new ValidationException(6);
		}
		return true;
	}

	@Override
	protected ResultSet performRequest(SqlExecutor sqlExc) throws SQLException, ExecutionException {
		final String username = this.creds.getUsername();
		ResultSet rset = sqlExc.executePreparedStatement("AddPermissionGroupForTable", new StatementPreparer() {
			@Override
			public void prepareStatement(PreparedStatement ps) throws SQLException {
				ps.setString(1, username);
				ps.setString(2, username);
				ps.setString(3, username);
			}
		});
		return null;
	}
}
