/**
 * FILE : UserSelectRequest.java AUTHORS : Erez Gotlieb DESCRIPTION :
 */
package Request.UserRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.DynamicStatements.SqlQueryGenerator;
import SQL.SqlExecutor;
import SQL.Utilities.Utils;
import Statement.AndStatement;
import Statement.RelStatement;
import Statement.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserSelectRequest extends UserRequest {

	Statement where;

	public UserSelectRequest(Statement where, Credentials creds) {
		this(creds);
		this.where = where;
	}

	public UserSelectRequest(Credentials creds) {
		super(creds);
	}

	@Override
	public USER_ACTION_TYPE getActionType() {
		return USER_ACTION_TYPE.SELECT;
	}

	@Override
	protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
		return true;
	}

	@Override
	protected ResultSet performRequest(SqlExecutor sqlExc) throws SQLException {
		final String userTable = "USERS";
		final String passwordField = "PASSWORD";
		final String usernameField = "USERNAME";
		List<String> userCols = Utils.getColNames(sqlExc, userTable);
		userCols.remove(passwordField);
		Statement whereNoAnon = new AndStatement(this.where, new RelStatement(usernameField, "'" + Credentials.anonymous + "'", "!="));
		return sqlExc.executeDynamicStatementQry(SqlQueryGenerator.select(userCols, userTable, whereNoAnon));
	}
}
