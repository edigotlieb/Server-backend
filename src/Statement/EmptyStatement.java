/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

/**
 *
 * @author T7639192
 */
public class EmptyStatement extends Statement {

	public EmptyStatement() {
	}

	@Override
	public String toString() {
		return "1";
	}

	@Override
	public boolean isColumnIn(String colname) {
		return false;
	}

	@Override
	public boolean validateOperands() {
		return true;
	}
}
