/**
 * FILE : UserAddPermissionGroupRequest.java AUTHORS : Erez Gotlieb DESCRIPTION
 * :
 */
package Request.UserRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import SQL.Utilities.ExistenceValidator;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAddPermissionGroupRequest extends UserRequest {

	String userToAddTo, groupName;

	public UserAddPermissionGroupRequest(String userToAddTo, String groupName, Credentials creds) {
		this(creds);
		this.userToAddTo = userToAddTo;
		this.groupName = groupName;
	}

	public UserAddPermissionGroupRequest(Credentials creds) {
		super(creds);
	}

	@Override
	public USER_ACTION_TYPE getActionType() {
		return USER_ACTION_TYPE.ADD_PERMISSION;
	}

	@Override
	protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
		// check user and group exists
		if (!ExistenceValidator.isUserByUsername(sqlExc, this.userToAddTo)) {
			throw new ValidationException(3);
		}

		String groupadmin = ExistenceValidator.permissionGroupByName(sqlExc, this.groupName);
		if (groupadmin.length() == 0) {
			throw new ValidationException(11);
		}
		// check if adder is group admin
		if (!groupadmin.equals(creds.getUsername())) {
			throw new ValidationException(6);
		}
		return true;
	}

    @Override
    protected ResultSet performRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
