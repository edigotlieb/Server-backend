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
			bytesOfMessage = myStr.getBytes("UTF-8");
		} catch (UnsupportedEncodingException ex) {
			return myStr;
		}
		byte[] thedigest = md5.digest(bytesOfMessage);
		return new String(thedigest);
	}

	public static String generateChallenge() {
		return UUID.randomUUID().toString();
	}
}
