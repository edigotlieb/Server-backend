/**
 * FILE : DTDInsertRequest.java AUTHORS : Erez Gotlieb DESCRIPTION :
 */
package Request.DTDRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.DynamicStatements.SqlQueryGenerator;
import SQL.SqlExecutor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DTDInsertRequest extends DTDRequest {

	Map<String, String> data;

	public DTDInsertRequest(String tableName, Map<String, String> data, Credentials creds) {
		super(creds, tableName);
		this.data = data;
	}

	@Override
	public DTD_ACTION_TYPE getActionType() {
		return DTD_ACTION_TYPE.INSERT;
	}

	@Override
	protected ResultSet performRequest(SqlExecutor sqlExc) throws SQLException {
		sqlExc.executeDynamicStatementQry(SqlQueryGenerator.insert(tableName, data));
		return null;
	}
}
