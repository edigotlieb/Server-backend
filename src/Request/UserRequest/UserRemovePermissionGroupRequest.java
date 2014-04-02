/**
 * FILE : UserRemovePermissionGroupRequest.java AUTHORS : Erez Gotlieb
 * DESCRIPTION :
 */
package Request.UserRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.PreparedStatements.StatementPreparer;
import SQL.SqlExecutor;
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

		final String group_name = this.groupToRemove;
		ResultSet rset = sqlExc.executePreparedStatement("getPermissionGroupInfoByName", new StatementPreparer() {
			@Override
			public void prepareStatement(PreparedStatement ps) throws SQLException {
				ps.setString(1, group_name);
			}
		});
		if (!rset.next()) {
			throw new ValidationException(11);
		}

		if (this.userToRemoveFrom.length() > 0) {
			final String user_name = this.userToRemoveFrom;
			ResultSet rset2 = sqlExc.executePreparedStatement("getAllUserInfoByUserName", new StatementPreparer() {
				@Override
				public void prepareStatement(PreparedStatement ps) throws SQLException {
					ps.setString(1, user_name);
				}
			});
			if (!rset2.next()) {
				throw new ValidationException(3);
			}
			
			// check if adder is super admin or group admin
			if (!rset.getString("USERNAME").equals(creds.getUsername())) {
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
