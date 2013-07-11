package eu.eurofel.util;

public interface Constants {

	public static String BASE_DN = "dc=umbrellaid,dc=org";
	
	public static String PEOPLE_DN = "ou=people," + Constants.BASE_DN;
	
	public static String NEW_PEOPLE_DN = "ou=newpeople," + Constants.BASE_DN;
	
}
