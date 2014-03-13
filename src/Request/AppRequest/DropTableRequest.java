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

public class DropTableRequest extends AppRequest {

	private String tableName;

	public DropTableRequest(Credentials creds, String tableName) {
		super(creds);
		this.tableName = tableName;
	}

	@Override
	protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException {
		final String table_name = this.tableName;
		ResultSet rset = sqlExc.executePreparedStatement("getTableInfoByName", new StatementPreparer() {
			@Override
			public void prepareStatement(PreparedStatement ps) throws SQLException {
				ps.setString(1, table_name);
			}
		});
		if (!rset.next()) {
			return false;
		}
		String app_name = rset.getString("APP_NAME");
		if (!this.creds.getAppName().equals(app_name)) {
			return false;
		}

		if (!this.creds.isAppSuperAdmin()) {
			return false;
		}
		return true;
	}

	@Override
	public APP_ACTION_TYPE getActionType() {
		return AppRequest.APP_ACTION_TYPE.DROP_TABLE;
	}
}
