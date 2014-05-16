package eu.eurofel.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.eurofel.util.EAAHash;


public class HashBirthdayTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		String[] list = {"a12", "abcd", "abcd3244efwe", "bjoern.abt@object.ch", "//asbd"};
//		
//		Pattern p = Pattern.compile("[a-zA-Z0-9]{4,}");
//		for(int i = 0; i < list.length; i++){
//			String exp = list[i];
//			Matcher m = p.matcher(exp);
//			System.out.println(exp + " matches? " + m.matches());
//		}
		
		System.out.println(EAAHash.getSHA1Hash("Ulrike.Lindemann@desy.de".toLowerCase()));

		System.out.println(EAAHash.getSHA1Hash("ulrike.lindemann@desy.de".toLowerCase()));
		
		// (&(mail=59f362223aba720619b0b88f2d26227728c3fdad)(uid=flowback))
//		System.out.println(EAAHash.getSHA1Hash("14c70b93-b986-44eb-862c-38dccd088a76403942730857196"));
//		System.out.println(cr.calculate("14c70b93-b986-44eb-862c-38dccd088a76", "403942", "730857196"));
		
//		System.out.println(Math.random());
//		System.out.println(UUID.randomUUID());
		
//		HashMap<String, String> map = new HashMap<String, String>();
//		long now = new Date().getTime();
//		GregorianCalendar cal = new GregorianCalendar();
//		cal.set(Calendar.YEAR, 1900);
//		long m1 = 0l;
//		String eaaHash = "";
//		String date = "";
//		while (m1 < now) {
//
//			m1 = cal.getTimeInMillis();
//			eaaHash = EAAHash.getSHA1Hash(new Long(m1).toString());
//			date = cal.get(Calendar.DAY_OF_MONTH) + "."
//					+ cal.get(Calendar.MONTH) + "." + cal.get(Calendar.YEAR);
//			if (map.containsKey(eaaHash)) {
//				System.out.println("Incident between: " + date + " :: "
//						+ map.get(eaaHash));
//			} else {
//				map.put(eaaHash, date);
//			}
//			cal.setTimeInMillis(m1 + 86400000);
//		}
	}

}
