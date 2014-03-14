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

public class DeleteAppRequest extends AppRequest {

	private String appName;

	public DeleteAppRequest(Credentials creds, String appName) {
		super(creds);
		this.appName = appName;
	}

	@Override
	protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException {
		final String app_name = this.appName;
		ResultSet rset = sqlExc.executePreparedStatement("getAllAppInfoByName", new StatementPreparer() {
			@Override
			public void prepareStatement(PreparedStatement ps) throws SQLException {
				ps.setString(1, app_name);
			}
		});
		if (!rset.next()) {
			return false;
		}

		if (!this.creds.isInPermissionGroup(appName + "_" + this.creds.getUsername())) {
			return false;
		}
		return true;
	}

	@Override
	public APP_ACTION_TYPE getActionType() {
		return AppRequest.APP_ACTION_TYPE.DELETE_APP;
	}
}
