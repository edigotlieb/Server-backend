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

public class GetAllAppsRequest extends AppRequest {

	public GetAllAppsRequest(Credentials creds) {
		super(creds);
	}

	@Override
	public APP_ACTION_TYPE getActionType() {
		return AppRequest.APP_ACTION_TYPE.GET_ALL_APPS;
	}

	@Override
	protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
		//if (!this.creds.isMasterApplication()) {
		//	throw new ValidationException(13);
		//}
		return true;
	}

	@Override
	protected ResultSet performRequest(SqlExecutor sqlExc) throws SQLException {
		return sqlExc.executePreparedStatement("getAllApps", new StatementPreparer() {
			@Override
			public void prepareStatement(PreparedStatement ps) throws SQLException {
			}
		});
	}
}
