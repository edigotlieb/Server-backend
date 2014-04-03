/**
 * FILE : SignInRequest.java AUTHORS : Erez Gotlieb DESCRIPTION :
 */
package Request.AppRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.DynamicStatements.SqlQueryGenerator;
import SQL.PreparedStatements.StatementPreparer;
import SQL.SqlExecutor;
import SQL.Utilities.ExistenceValidator;
import SQL.Utilities.Utils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddTableRequest extends AppRequest {

	private final List<Column> columns;
	private final List<Permission> perms;
	private final String tableName;

	public AddTableRequest(Credentials creds, String tableName, List<Column> cols, List<Permission> perms) {
		super(creds);
		this.columns = new ArrayList<>(cols);
		this.perms = new ArrayList<>(perms);
		this.tableName = tableName;
	}

	@Override
	protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
		if (ExistenceValidator.isTableByName(sqlExc, this.tableName)) {
			//table exists
			throw new ValidationException(7);
		}

		for (Permission per : perms) {
			if (!ExistenceValidator.isPermissionGroupByName(sqlExc, per.getPermissionGroup())) {
				throw new ValidationException(9);
			}
		}

		for (Column col : columns) {
			if (!Utils.isAlphaNumeric(col.getColName())) {
				throw new ValidationException(15);
			}
		}

		if (!Utils.isAlphaNumeric(tableName)) {
			throw new ValidationException(15);
		}

		if (!this.creds.isAppSuperAdmin()) {
			throw new ValidationException(6);
		}
		return true;
	}

	@Override
	public APP_ACTION_TYPE getActionType() {
		return AppRequest.APP_ACTION_TYPE.ADD_TABLE;
	}

	@Override
	protected ResultSet performRequest(SqlExecutor sqlExc) throws SQLException {
		final String table_name = this.creds.getAppName() + "_" + tableName;
		final String app_name = this.creds.getAppName();
		sqlExc.executePreparedStatement("AddPermissionGroupForTable", new StatementPreparer() {
			@Override
			public void prepareStatement(PreparedStatement ps) throws SQLException {
				ps.setString(1, table_name);
				ps.setString(2, app_name);
			}
		});
		sqlExc.executeDynamicStatementQry(SqlQueryGenerator.create(tableName, columns));

		for (Permission per : perms) {
			final String appname = per.getAppName();
			final String tablename = per.getTableName();
			final String permission_type = per.getType().toString();
			final String permission_name = per.getPermissionGroup();
			sqlExc.executePreparedStatement("AddPermissionGroupForTable", new StatementPreparer() {
				@Override
				public void prepareStatement(PreparedStatement ps) throws SQLException {
					ps.setString(1, appname);
					ps.setString(2, tablename);
					ps.setString(3, permission_type);
					ps.setString(4, permission_name);
				}
			});
		}

		return null;
	}
}
