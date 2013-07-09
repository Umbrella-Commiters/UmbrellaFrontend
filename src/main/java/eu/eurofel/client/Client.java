package eu.eurofel.client;

import java.util.Calendar;
import java.util.GregorianCalendar;

import eu.eurofel.util.EAAHash;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// we have to remove all time information from the date...
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.set(Calendar.HOUR, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);

		String ex = EAAHash.getSHA1Hash(new Long(cal1.getTime().getTime())
				.toString());

		boolean run = true;

		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, 1900);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		while (run) {
			cal.add(Calendar.DAY_OF_YEAR, 1);
			if (EAAHash.getSHA1Hash(
					new Long(cal.getTime().getTime()).toString()).equals(ex)) {
				System.out.println(cal);
				run = false;
			}

			if (cal.get(Calendar.YEAR) > 2011) {
				run = false;
			}
		}
	}

}
