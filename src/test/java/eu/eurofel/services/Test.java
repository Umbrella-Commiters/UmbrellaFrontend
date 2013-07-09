package eu.eurofel.services;

import java.util.Date;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long date = new Date().getTime();
		System.out.println(date);
		System.out.println(new Long(date));
	}

}
