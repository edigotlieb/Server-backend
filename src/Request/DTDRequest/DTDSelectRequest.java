/**
 * FILE : DTDSelectRequest.java AUTHORS : Erez Gotlieb DESCRIPTION :
 */
package Request.DTDRequest;

import Request.Credentials;
import SQL.DynamicStatements.SqlQueryGenerator;
import SQL.SqlExecutor;
import Statement.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DTDSelectRequest extends DTDRequest {

	Statement where;

	public DTDSelectRequest(String tableName, Statement where, Credentials creds) {
		super(creds, tableName);
		this.where = where;
	}

	public boolean validateOpernads() {
		return this.where.validateOperands();
	}

	@Override
	public DTD_ACTION_TYPE getActionType() {
		return DTD_ACTION_TYPE.SELECT;
	}

	@Override
	protected ResultSet performRequest(SqlExecutor sqlExc) throws SQLException {
		return sqlExc.executeDynamicStatementQry(SqlQueryGenerator.select(null, tableName, where));
	}
}
