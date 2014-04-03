/** 
 * FILE : SqlExecutor.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package SQL;


import SQL.PreparedStatements.PreparedStatementStrings;
import SQL.PreparedStatements.StatementPreparer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlExecutor {

    Connection con;
    PreparedStatementStrings sqls = PreparedStatementStrings.getInstance();
    
    
    public SqlExecutor(Connection con) {
        this.con = con;
    }
    
    public ResultSet executePreparedStatement(String sqlKey,StatementPreparer sp) throws SQLException {
        PreparedStatement ps = this.con.prepareStatement(this.sqls.getSQL(sqlKey));
        return executePreparedStatementQry(ps);
    }            
    
    private ResultSet executePreparedStatementQry(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        ps.close();
        return rs;
    }
    
    public ResultSet executeDynamicStatementQry(String sql) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        stmt.close();
        return rs;
    }
    
    
}
