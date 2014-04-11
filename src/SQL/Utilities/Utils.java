/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL.Utilities;

import SQL.DynamicStatements.SqlQueryGenerator;
import SQL.SqlExecutor;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author t7639192
 */
public class Utils {

	public static String sanitizeAlphaNumeric(String org) {
		return org.replaceAll("[^\\w]", "");
	}

	public static String sanitizeAlphaNumericSpecialChar(String org) {
		String hebrewChars = "אבגדהוזחטיכךלמםנןסעפףצץקרשת";
		String special = "\\ \\.\\*\\+\\-\\(\\)\\[\\]\\{\\}\\\\" + hebrewChars;
		return org.replaceAll("[^\\w" + special + "]", "");
	}

	public static boolean isAlphaNumeric(String org) {
		return org.matches("[\\w]+");
	}

	public static String toString(Date date) {

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return sdf.format(date);
	}

	public static List<String> getColNames(SqlExecutor sqlExc, String table) throws SQLException {

		List<String> cols = new ArrayList<>();
		ResultSet rset = sqlExc.executeDynamicStatementQry(SqlQueryGenerator.desc(table));
		while (rset.next()) {
			cols.add(rset.getString(1));
		}
		return cols;
	}
}
