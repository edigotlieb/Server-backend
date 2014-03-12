/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

/**
 *
 * @author T7639192
 */
abstract public class Statement {

	public static Statement StatementFactory(String statement) {
		return null;
	}

	@Override
	abstract public String toString();
}
