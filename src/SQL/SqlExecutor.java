/** 
 * FILE : SqlExecutor.java
 * AUTHORS : Erez Gotlieb    
 * DESCRIPTION : 
 */ 

package SQL;


import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlExecutor {

    
    
    public static ResultSet executePreparedStatementQry(Connection con,PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        ps.close();
        return rs;
    }
    
    public static ResultSet executeDynamicStatementQry(Connection con,String sql) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        stmt.close();
        return rs;
    }
    
    
}
