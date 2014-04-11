/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

/**
 *
 * @author T7639192
 */
public class OrStatement extends Statement {

	private final Statement st1, st2;

	public OrStatement(Statement st1, Statement st2) {
		this.st1 = st1;
		this.st2 = st2;
	}

	@Override
	public String toString() {
		return "(" + st1.toString() + ") OR (" + st2.toString() + ")";
	}

	@Override
	public boolean isColumnIn(String colname) {
		return st1.isColumnIn(colname) || st2.isColumnIn(colname);
	}
}
