/**
 * FILE : SetPermissionGroupAdminRequest.java AUTHORS : Erez Gotlieb DESCRIPTION
 * :
 */
package Request.AppRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import SQL.Utilities.ExistenceValidator;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SetPermissionGroupAdminRequest extends AppRequest {

	String username, groupName;

	public SetPermissionGroupAdminRequest(Credentials creds) {
		super(creds);
	}

	public SetPermissionGroupAdminRequest(String username, String groupName, Credentials creds) {
		this(creds);
		this.username = username;
		this.groupName = groupName;
	}

	@Override
	public APP_ACTION_TYPE getActionType() {
		return APP_ACTION_TYPE.SET_PERMISSIONGROUP_ADMIN;
	}

	@Override
	protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
		// check if the user is the current permission group admin
		if (!ExistenceValidator.isUserByUsername(sqlExc, username)) {
			throw new ValidationException(3);
		}
		String curGroupAdmin = ExistenceValidator.permissionGroupByName(sqlExc, groupName);
		if (curGroupAdmin.length() == 0) {
			throw new ValidationException(9);
		}

		if (!this.creds.getUsername().equals(curGroupAdmin)) {
			throw new ValidationException(6);
		}

		return false;
	}

	@Override
	protected ResultSet performRequest(SqlExecutor sqlExc) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
