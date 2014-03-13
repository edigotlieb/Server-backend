/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL.DynamicStatements;

import Request.AppRequest.AddTableRequest;
import Statement.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author T7639192
 */
public class SqlQueryGenerator {

	private static <T> String stringCommaSeperated(Iterable<T> list){
		Iterator<T> iterator = list.iterator();
		String res = "";
		while(iterator.hasNext()){
			res += iterator.next().toString();
			res += ", ";
		}
		if(res.length() > 0){
			res = res.substring(0, res.length()-2);
		}
		return res;
	}
	
	private static <T> String stringCommaSeperated(Map<T,T> map){
		Iterator<T> iterator = map.keySet().iterator();
		String res = "";
		while(iterator.hasNext()){
			T key = iterator.next();
			res += key.toString() + " = " + map.get(key);
			res += ", ";
		}
		if(res.length() > 0){
			res = res.substring(0, res.length()-2);
		}
		return res;
	}
	
	public static String select(List<String> colnames, String from, Statement where) {
		return "SELECT " + stringCommaSeperated(colnames) + " FROM " + from + " WHERE " + where.toString();
	}

	public static String delete(String from, Statement where) {
		return "DELETE FROM " + from + " WHERE " + where.toString();
	}

	public static String update(String table, Map<String, String> set, Statement where) {
		return "UPDATE " + table + " SET " + stringCommaSeperated(set) + " WHERE " + where.toString();
	}

	public static String insert(String into, List<String> values) {
		return "INSERT INTO " + into + " VALUES (" + stringCommaSeperated(values) + ")";
	}

	public static String insert(String into, Map<String, String> cols_vals) {
		return "INSERT INTO " + into + "(" + stringCommaSeperated(cols_vals.keySet())  + ") VALUES (" + stringCommaSeperated(cols_vals.values()) + ")";
	}
	
	public static String create(String tableName, List<AddTableRequest.Column> cols) {
		return "CREATE TABLE " + tableName + "(" + stringCommaSeperated(cols)  + ")";
	}
	
	public static String drop(String tableName) {
		return "DROP TABLE " + tableName;
	}
}
