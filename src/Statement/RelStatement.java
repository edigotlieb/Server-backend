/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

import SQL.Utilities.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author T7639192
 */
public class RelStatement extends Statement {

	private final String term1, term2, op;
	private static final List<String> operands = new ArrayList<>();

	static {

		RelStatement.operands.add("=");
		RelStatement.operands.add("!=");
		RelStatement.operands.add(">=");
		RelStatement.operands.add("<=");
		RelStatement.operands.add(">");
		RelStatement.operands.add("<");
		RelStatement.operands.add("LIKE");
		RelStatement.operands.add("NOT LIKE");
		RelStatement.operands.add("NOT NULL");
	}

	public RelStatement(String term1, String term2, String op) {
		this.term1 = sanitizeTerm(term1);
		this.term2 = sanitizeTerm(term2);
		this.op = op;

		if (!operands.contains(op)) {
			throw new IllegalArgumentException();
		}
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
	public boolean isColumnIn(String colname) {
		return term1.equals(colname) || term2.equals(colname);
	}

	@Override
	public boolean validateOperands() {
		return operands.contains(op);
	}

	@Override
	public String toString() {
		return term1 + " " + op + " " + term2;
	}
}
