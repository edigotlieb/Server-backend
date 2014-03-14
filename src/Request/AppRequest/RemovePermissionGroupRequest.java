/**
 * FILE : SignInRequest.java AUTHORS : Erez Gotlieb DESCRIPTION :
 */
package Request.AppRequest;

import Request.Credentials;
import SQL.PreparedStatements.StatementPreparer;
import SQL.SqlExecutor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemovePermissionGroupRequest extends AppRequest {

	private String permissionGroupName;

	public RemovePermissionGroupRequest(Credentials creds, String permissionGroupName) {
		super(creds);
		this.permissionGroupName = permissionGroupName;
	}

	@Override
	protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException {
		final String permissionGroupNameLocal = this.permissionGroupName;
		ResultSet rset = sqlExc.executePreparedStatement("getPermissionGroupInfoByName", new StatementPreparer() {
			@Override
			public void prepareStatement(PreparedStatement ps) throws SQLException {
				ps.setString(1, permissionGroupNameLocal);
			}
		});
		if (!rset.next()) {
			return false;
		}
		String username = rset.getString("USERNAME");
		if (!this.creds.getUsername().equals(username)) {
			return false;
		}

		if (!this.creds.isAppSuperAdmin()) {
			return false;
		}
		return true;
	}

	@Override
	public APP_ACTION_TYPE getActionType() {
		return AppRequest.APP_ACTION_TYPE.REMOVE_PERMISSIONGROUP;
	}

	public class Column {

		private String colName, dataType;
		private int size;

		public Column(String colName, String dataType, int size) {
			this.colName = colName;
			this.dataType = dataType;
			this.size = size;
		}

		@Override
		public String toString() {
			return colName + " " + dataType + "(" + size + ")";
		}
	}
}
