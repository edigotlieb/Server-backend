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
import java.util.ArrayList;
import java.util.List;

public class AddTableRequest extends AppRequest {

	private final List<Column> columns;
	private final List<Permission> perms;
	private final String tableName;

	public enum PERMISSION_TYPE {
		ADD_TABLE, DROP_TABLE, DELETE_APP, ADD_PERMISSIONGROUP, REMOVE_PERMISSIONGROUP
	}

	public AddTableRequest(Credentials creds, String tableName, List<Column> cols, List<Permission> perms) {
		super(creds);
		this.columns = new ArrayList<>(cols);
		this.perms = new ArrayList<>(perms);
		this.tableName = this.creds.getAppName()+"_"+tableName;
	}

	@Override
	protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException,ValidationException {
		final String table_name = this.tableName;
		ResultSet rset = sqlExc.executePreparedStatement("getTableInfoByName", new StatementPreparer() {
			@Override
			public void prepareStatement(PreparedStatement ps) throws SQLException {
				ps.setString(1, table_name);
			}
		});
		if (rset.next()) {
                        //table exists
                        throw new ValidationException(7);			
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

	public class Permission {

		private PERMISSION_TYPE type;
		private String appName, tableName, permissionGroup;

		public Permission(PERMISSION_TYPE type, String appName, String tableName, String permissionGroup) {
			this.type = type;
			this.appName = appName;
			this.tableName = tableName;
			this.permissionGroup = permissionGroup;
		}
	}
}
