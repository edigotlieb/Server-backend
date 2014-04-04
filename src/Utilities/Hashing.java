package Utilities;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author T7639192
 */
public class Hashing {

	static MessageDigest md5;

	static {
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
		}
	}

	public static String MD5Hash(String myStr) {
		byte[] bytesOfMessage;
		try {
			bytesOfMessage = myStr.getBytes("ascii");
		} catch (UnsupportedEncodingException ex) {
			return myStr;
		}
		byte[] thedigest = md5.digest(bytesOfMessage);
                return toHex(thedigest);           
	}
        
        public static String toHex(byte[] in) {
        StringBuilder out = new StringBuilder(in.length * 2);
        for (byte b: in) {
            out.append(String.format("%02X", (byte) b));
        }
        return out.toString().toLowerCase();
         }
        
        

	public static String generateChallenge() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
