package eu.eurofel.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

public final class EAAHash {

	public static String getSHA1Hash(String value) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return EAAHash.calculateHash(md, value);

	}

	private static String calculateHash(MessageDigest algorithm, String value) {

		// get the hash value as byte array
		byte[] hash = algorithm.digest(value.getBytes());

		return byteArray2Hex(hash);
	}

	private static String byteArray2Hex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}

	public static void main(String[] args) {
		System.out.println(EAAHash.getSHA1Hash("Test"));
		System.out.println(UUID.randomUUID().toString().length());
	}

}
