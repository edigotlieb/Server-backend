/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

import SQL.Utilities.Utils;

/**
 *
 * @author T7639192
 */
public class RelStatement extends Statement {

	private final String term1, term2, op;

	public RelStatement(String term1, String term2, String op) {
		this.term1 = sanitizeTerm(term1);
		this.term2 = sanitizeTerm(term2);
		this.op = op;
	}

	/**
	 * only place a single quote may appear is at the beginning or end of a term
	 * all other non alpha-numeric characters are removed
	 *
	 * @param term
	 * @return
	 */
	private static String sanitizeTerm(String term) {
		if (term.matches("'[^']*'")) {
			return '\'' + Utils.sanitizeAlphaNumericSpecialChar(term) + '\'';
		} else {
			return Utils.sanitizeAlphaNumeric(term);
		}
	}

	@Override
	public String toString() {
		return term1 + " " + op + " " + term2;
	}
}
