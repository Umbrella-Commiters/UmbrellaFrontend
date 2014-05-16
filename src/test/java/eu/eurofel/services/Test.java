package eu.eurofel.services;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;





public class Test {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		String value = "dXNlcjI6dXNlcjI=";
		System.out.println("Base64-encoded Authorization Value: <em>" + value);
		byte[] decodedValue = Base64.decodeBase64(value);
		System.out.println(new String(Base64.decodeBase64(value), "UTF-8"));
	}

}
