/**
 * FILE : SignInRequest.java AUTHORS : Erez Gotlieb DESCRIPTION :
 */
package Request.AppRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.PreparedStatements.StatementPreparer;
import SQL.SqlExecutor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DropTableRequest extends AppRequest {

	private String tableName;

	public DropTableRequest(Credentials creds, String tableName) {
		super(creds);
		this.tableName = this.creds.getAppName() + "_" + tableName;
	}

	@Override
	protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
		final String table_name = this.tableName;
		ResultSet rset = sqlExc.executePreparedStatement("getTableInfoByName", new StatementPreparer() {
			@Override
			public void prepareStatement(PreparedStatement ps) throws SQLException {
				ps.setString(1, table_name);
			}
		});
		if (!rset.next()) {
			throw new ValidationException(8);
		}
		if (!this.creds.isAppSuperAdmin()) {
			throw new ValidationException(6);
		}
		return true;
	}

	@Override
	public APP_ACTION_TYPE getActionType() {
		return AppRequest.APP_ACTION_TYPE.DROP_TABLE;
	}
}
