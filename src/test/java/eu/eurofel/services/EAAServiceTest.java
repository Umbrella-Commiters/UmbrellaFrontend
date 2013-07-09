/**
 * 
 */
package eu.eurofel.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.UUID;

import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.eurofel.entities.EAAAccount;
import eu.eurofel.services.impl.EAAServiceImpl;

/**
 * @author bjoern
 * 
 */
public class EAAServiceTest {

	private EAAService service = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		if (service == null) {
			service = new EAAServiceImpl();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link eu.eurofel.services.impl.EAAServiceImpl#createAccount(eu.eurofel.entities.EAAAccount)}
	 * .
	 * 
	 * @throws NamingException
	 */
	@Test
	public void testCreateAccount() throws Exception {
		String uid = UUID.randomUUID().toString().replaceAll("-", "");
		try {
			// This one should throw an exception
			service.isAccountAvailable(uid);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue("This should not throw an exception", false);
		}
		EAAAccount adr = getAddress(uid);
		service.createAccount(adr);

		try {
			// This one should throw an exception
			service.isAccountAvailable(uid);
		} catch (Exception e) {
			assertEquals("Account exists", e.getLocalizedMessage());
		}

	}

	/**
	 * Test method for
	 * {@link eu.eurofel.services.impl.EAAServiceImpl#updateAccount(eu.eurofel.entities.EAAAccount)}
	 * .
	 * 
	 * @throws NamingException
	 */
	@Test
	public void testUpdateAccount() throws Exception {
		String uid = UUID.randomUUID().toString().replaceAll("-", "");
		String email = uid + "@test.ch";

		EAAAccount adr = getAddress(uid);
		service.createAccount(adr);
		adr = new EAAAccount(service.findAccount(uid));
		adr.setEmail(email);

		// now email should not be available
		try {
			service.isEmailAvailable(email);
			assertTrue("E-Mail does exist!", true);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue("E-Mail does exist!", false);
		}

		service.updateAccount(adr);

		// and now an exception should be thrown
		try {
			service.isEmailAvailable(email);
		} catch (Exception e) {
			assertEquals("Email exists", e.getLocalizedMessage());
		}
	}

	/**
	 * Test method for
	 * {@link eu.eurofel.services.impl.EAAServiceImpl#findAccount(java.lang.String)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindAccount() throws Exception {
		String uid = UUID.randomUUID().toString().replaceAll("-", "");
		try {
			service.findAccount(uid);
			assertTrue("", false);
		} catch (NamingException e) {
			assertTrue("Account exists!", true);
		}
		EAAAccount adr = getAddress(uid);
		service.createAccount(adr);
		try {
			service.findAccount(uid);
			assertTrue("Account exists!", true);
		} catch (Exception e) {
			assertTrue("Account doesn't exist!", false);
			e.printStackTrace();
		}

	}

	/**
	 * Test method for
	 * {@link eu.eurofel.services.impl.EAAServiceImpl#isAccountAvailable(java.lang.String)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIsAccountAvailable() throws Exception {
		String uid = UUID.randomUUID().toString();
		EAAAccount adr = getAddress(uid);

		// Account should not exist yet.
		try {
			service.isAccountAvailable(uid);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}

		service.createAccount(adr);

		// now Account should exist.
		try {
			service.isAccountAvailable(uid);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	/**
	 * Test method for
	 * {@link eu.eurofel.services.impl.EAAServiceImpl#isEmailAvailable(java.lang.String)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIsEmailAvailable() throws Exception {
		String uid = UUID.randomUUID().toString();
		EAAAccount adr = getAddress(uid);
		adr.setEmail(uid + "@test.ch");

		// Account should not exist yet.
		try {
			service.isEmailAvailable(adr.getEmail());
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}

		service.createAccount(adr);

		System.out.println(adr.getEmail());
		// now Account should exist.
		try {
			service.isEmailAvailable(adr.getEmail());
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	private EAAAccount getAddress(String uid) {
		EAAAccount adr = new EAAAccount();
		adr.setUid(uid);
		adr.setEaahash("EAAHASH");
		adr.setBirthdate(new Date());
		return adr;
	}
}
