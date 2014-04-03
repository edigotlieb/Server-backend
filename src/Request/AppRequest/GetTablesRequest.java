/**
 * FILE : GetTablesInfo.java AUTHORS : Erez Gotlieb DESCRIPTION :
 */
package Request.AppRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.PreparedStatements.StatementPreparer;
import SQL.SqlExecutor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetTablesRequest extends AppRequest {

	public GetTablesRequest(Credentials creds) {
		super(creds);
	}

	@Override
	public APP_ACTION_TYPE getActionType() {
		return AppRequest.APP_ACTION_TYPE.GET_TABLES;
	}

	@Override
	protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
		if (!this.creds.isAppSuperAdmin()) {
			throw new ValidationException(6);
		}
		return true;
	}

	@Override
	protected ResultSet performRequest(SqlExecutor sqlExc) throws SQLException {
		final String appName = this.creds.getAppName();
		return sqlExc.executePreparedStatement("getAppTables", new StatementPreparer() {
			@Override
			public void prepareStatement(PreparedStatement ps) throws SQLException {
				ps.setString(1, appName);
			}
		});
	}
}
