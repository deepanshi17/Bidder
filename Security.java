package assignment7Server;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;


public class Security {
	
	private static final String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	protected static Random rand = new Random();
		
	protected static String getSalt(int length) {
		StringBuilder ret = new StringBuilder(length);
		for(int i = 0; i < length; i++) {
			ret.append(alpha.charAt(rand.nextInt(alpha.length())));
		}
		return new String(ret);
	}
	
	protected static String generateSecurePassword(String password, String salt) throws NoSuchAlgorithmException {
		byte[] securePassword = hash(password, salt);
		return Base64.getEncoder().encodeToString(securePassword);
	}
	
	protected static boolean verifyPassword(String password, String securePassword, String salt) throws NoSuchAlgorithmException {
		// generate new secure password using the same salt as original password
		String newSecurePassword = generateSecurePassword(password, salt);
		
		// check if the two are equal
		return newSecurePassword.equalsIgnoreCase(securePassword);
		
	}
	
	protected static byte[] hash(String password, String salt) throws NoSuchAlgorithmException {
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 1000, 256);
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			return skf.generateSecret(spec).getEncoded();
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		finally {
			spec.clearPassword();
		}
		return salt.getBytes();
	}
	

}
