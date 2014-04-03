/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL.Utilities;

/**
 *
 * @author t7639192
 */
public class Utils {
	public static String sanitizeAlphaNumeric(String org){
		return org.replaceAll("[^\\w\\d]", "");
	}
	
	public static boolean isAlphaNumeric(String org){
		return org.matches("[\\w\\d]+");
	}
}
