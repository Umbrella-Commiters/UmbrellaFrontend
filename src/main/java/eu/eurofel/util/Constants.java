package eu.eurofel.util;

public interface Constants {

	public static String BASE_DN = "dc=eurofel,dc=eu";
	
	public static String PEOPLE_DN = "ou=people," + Constants.BASE_DN;

	public static String NEW_PEOPLE_DN = "ou=newpeople," + Constants.BASE_DN;
	
	public static String BRIDGE_DN = "ou=bridge," + Constants.BASE_DN;
	
}
