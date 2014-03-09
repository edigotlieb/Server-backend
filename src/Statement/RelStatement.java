/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

/**
 *
 * @author T7639192
 */
public class RelStatement extends Statement {

	private String colname, value, op;

	public RelStatement(String colname, String value, String op) {
		this.colname = colname;
		this.value = value;
		this.op = op;
	}

	public String toString() {
		return colname + " " + op + " " + value;
	}
}
