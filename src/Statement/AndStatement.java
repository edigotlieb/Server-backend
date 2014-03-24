/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

/**
 *
 * @author T7639192
 */
public class AndStatement extends Statement{

	private final Statement st1, st2;

	public AndStatement(Statement st1, Statement st2) {
		this.st1 = st1;
		this.st2 = st2;
	}
	
        @Override
	public String toString(){
		return "(" + st1.toString() + ") AND (" + st2.toString() + ")";
	}
}
