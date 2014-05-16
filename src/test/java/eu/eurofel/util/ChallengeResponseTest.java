package eu.eurofel.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChallengeResponseTest {

	private final static String key = "14c70b93-b986-44eb-862c-38dccd088a76";
	private final static String challenge = "459967532";
	private final static String rand = "459967532";

	// private final static String expected_result =
	// "bf8e66e3ac541dccf3ca6346fe0e0b5aee9e6169";

	public static void main(String[] args) {
		ChallengeResponse r = new ChallengeResponseImpl();
		System.out.println(r.calculate("f1ab6415-aa4e-40ca-8386-c8c99d2eae8f", "141700", "2123632446"));
	}
	
	private ChallengeResponse cr;

	@Before
	public void setUp() throws Exception {
		cr = new ChallengeResponseImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTest(){
		System.out.println(cr.calculate("", challenge, rand));
	}
	
	@Test
	public void testCalculate() {
		System.out.println(cr.calculate(key, challenge, rand));

		System.out.println(cr.calculate("14c70b93-b986-44eb-862c-38dccd088a76",
				"403942", "730857196"));
		// assertEquals(expected_result, cr.calculate(key, challenge, rand));
	}

	@Test
	public void testTransmission() {
		String challenge = cr.generateChallenge();
		String returnvalue = cr.handle("", challenge);
		System.out.println(returnvalue);
		String localval = cr.calculate(key, challenge,
				returnvalue.split("\\|")[0]);
		System.out.println(localval + "::" + returnvalue.split("\\|")[1]);

	}
}
