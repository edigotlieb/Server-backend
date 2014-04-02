/**
 * FILE : UserRemovePermissionGroupRequest.java AUTHORS : Erez Gotlieb
 * DESCRIPTION :
 */
package Request.UserRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.PreparedStatements.StatementPreparer;
import SQL.SqlExecutor;
import SQL.Utilities.ExistenceValidator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRemovePermissionGroupRequest extends UserRequest {

	String userToRemoveFrom, groupToRemove;

	public UserRemovePermissionGroupRequest(String userToRemoveFrom, String groupToRemove, Credentials creds) {
		this(creds);
		this.userToRemoveFrom = userToRemoveFrom;
		this.groupToRemove = groupToRemove;
	}

	public UserRemovePermissionGroupRequest(Credentials creds) {
		super(creds);
	}

	@Override
	public USER_ACTION_TYPE getActionType() {
		return USER_ACTION_TYPE.REMOVE_PERMISSION;
	}

	@Override
	protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
		// check user and group exists
		String groupAdmin = ExistenceValidator.permissionGroupByName(sqlExc, this.groupToRemove);
		if (groupAdmin.length() == 0) {
			throw new ValidationException(11);
		}

		if (this.userToRemoveFrom.length() > 0) {
			if (!ExistenceValidator.isUserByUsername(sqlExc, this.userToRemoveFrom)) {
				throw new ValidationException(3);
			}
			
			// check if adder is super admin or group admin
			if (!groupAdmin.equals(creds.getUsername())) {
				throw new ValidationException(6);
			}
		} else {
			this.userToRemoveFrom = creds.getUsername();
		}

		return true;
	}

    @Override
    protected ResultSet performRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
