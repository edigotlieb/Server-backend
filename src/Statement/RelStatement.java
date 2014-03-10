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

	private final String term1, term2, op;

	public RelStatement(String term1, String term2, String op) {
		this.term1 = term1;
		this.term2 = term2;
		this.op = op;
	}

        @Override
	public String toString() {
		return term1 + " " + op + " " + term2;
	}
}
