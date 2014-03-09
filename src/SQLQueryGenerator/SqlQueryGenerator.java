/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLQueryGenerator;

import Statement.Statement;
import java.util.List;
import java.util.Map;

/**
 *
 * @author T7639192
 */
public class SqlQueryGenerator {

	public static String select(List<String> colnames, String from, Statement where) {
		return null;
	}

	public static String delete(String from, Statement where) {
		return null;
	}

	public static String update(String table, Map<String, String> set, Statement where) {
		return null;
	}

	public static String insert(String into, List<String> values) {
		return null;
	}

	public static String insert(String into, Map<String, String> cols_vals) {
		return null;
	}
}
