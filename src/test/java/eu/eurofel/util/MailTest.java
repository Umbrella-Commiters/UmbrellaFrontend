package eu.eurofel.util;

public class MailTest {

	public static void main(String[] args) {
//		System.out.println("(&(mail=" + EAAHash.getSHA1Hash("flowedback@gmail.com".toLowerCase())
//				+ ")(uid=" + "flowback" + "))");
		System.out.println(EAAHash.getSHA1Hash("shova.neupane@uhasselt.be".toLowerCase()));
		System.out.println(EAAHash.getSHA1Hash("flowedback@gmail.com".toLowerCase()));
	}

}
