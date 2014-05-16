package eu.eurofel.services;

import java.util.regex.Pattern;

public class CheckDateTest {
	public static void main(String[] args) {

		String pw = "P!7rH400";

		String pattern = "(?=[@#!$%&+=-_a-zA-Z0-9]*?[A-Z])(?=[@#!$%&+=-_a-zA-Z0-9]*?[a-z])(?=[@#!$%&+=-_a-zA-Z0-9]*?[0-9])(?=[@#!$%&+=-_a-zA-Z0-9]*?[@#!$%&+=-_])[@#!$%&+=-_a-zA-Z0-9]{8,}";
		Pattern pwPattern = Pattern.compile(pattern);

		System.out.println(pw + " matches: " + pwPattern.matcher(pw).matches());
		//It requires length of 8, at least one digit, one special character and mixed case.

	}
}
