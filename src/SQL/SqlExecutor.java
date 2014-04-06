/**
 * FILE : SqlExecutor.java AUTHORS : Erez Gotlieb DESCRIPTION :
 */
package SQL;

import SQL.PreparedStatements.PreparedStatementStrings;
import SQL.PreparedStatements.StatementPreparer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlExecutor {

	Connection con;
	PreparedStatementStrings sqls = PreparedStatementStrings.getInstance();
	static final Logger log = Logger.getGlobal();

	public SqlExecutor(Connection con) {
		this.con = con;
	}

	public ResultSet executePreparedStatement(String sqlKey, StatementPreparer sp) throws SQLException {
		PreparedStatement ps = this.con.prepareStatement(this.sqls.getSQL(sqlKey));
		sp.prepareStatement(ps);
		return executePreparedStatementQry(ps);
	}

	private ResultSet executePreparedStatementQry(PreparedStatement ps) throws SQLException {
		//log.log(Level.INFO, ps.toString());
		ps.execute();
		return ps.getResultSet();
	}

	public ResultSet executeDynamicStatementQry(String sql) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		// stmt.close();
		return rs;
	}
}
