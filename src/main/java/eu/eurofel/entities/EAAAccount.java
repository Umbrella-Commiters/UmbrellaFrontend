package eu.eurofel.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.tapestry5.beaneditor.Validate;

import eu.eurofel.util.EAAHash;

public class EAAAccount implements Serializable, DirContext {

	/**
	 * 
	 */

	private static final long serialVersionUID = 329431858022051155L;

	private String target = "";
	
	private String verificationCode = "";

	private String uid = "";

	private String email = "";

	private String password = "";

	private String password1 = "";

	private Date birthdate;

	private String eaahash = "";
	private String eaakey = "";

	private String eaaresetpwuuid;

	private String eaaresetpwdate;

	String dn;

	String id;

	Attributes myAttrs = new BasicAttributes(true);

	Attribute oc = new BasicAttribute("objectclass");

	Attribute ouSet = new BasicAttribute("ou");

	public EAAAccount() {
		oc.add("EAAUser");
		oc.add("top");
		ouSet.add("people");
		ouSet.add("ou=people,dc=eurofel,dc=ch");
		myAttrs.put(oc);
		myAttrs.put(ouSet);

	}

	public EAAAccount(Attributes attributes) throws NamingException {
		NamingEnumeration<? extends Attribute> results = attributes.getAll();
		while (results.hasMoreElements()) {
			Attribute rs = results.next();

			if (rs.getID().equals("mail")) {
				setEmail((String) rs.get());
			}
			if (rs.getID().equals("uid")) {
				setUid((String) rs.get());
			}

		}

	}

	public EAAAccount(String uid, String email) {
		setUid(uid);
		setEmail(email);

		oc.add("inetOrgPerson");
		oc.add("organizationalPerson");
		oc.add("person");
		oc.add("top");
		ouSet.add("people");
		ouSet.add("ou=people,dc=eurofel,dc=ch");
		myAttrs.put(oc);
		myAttrs.put(ouSet);

		myAttrs.put("userPassword", "abcd1234");
	}

	public Object addToEnvironment(String arg0, Object arg1) throws NamingException {

		return null;
	}

	public void bind(Name arg0, Object arg1) throws NamingException {

	}

	public void bind(Name arg0, Object arg1, Attributes arg2) throws NamingException {

	}

	public void bind(String arg0, Object arg1) throws NamingException {

	}

	public void bind(String arg0, Object arg1, Attributes arg2) throws NamingException {

	}

	public void close() throws NamingException {

	}

	public Name composeName(Name arg0, Name arg1) throws NamingException {

		return null;
	}

	public String composeName(String arg0, String arg1) throws NamingException {

		return null;
	}

	public Context createSubcontext(Name arg0) throws NamingException {

		return null;
	}

	public DirContext createSubcontext(Name arg0, Attributes arg1) throws NamingException {

		return null;
	}

	public Context createSubcontext(String arg0) throws NamingException {

		return null;
	}

	public DirContext createSubcontext(String arg0, Attributes arg1) throws NamingException {

		return null;
	}

	public void destroySubcontext(Name arg0) throws NamingException {

	}

	public void destroySubcontext(String arg0) throws NamingException {

	}

	public Attributes getAttributes(Name name) throws NamingException {

		return getAttributes(name.toString());
	}

	public Attributes getAttributes(Name name, String[] ids) throws NamingException {
		return getAttributes(name.toString(), ids);
	}

	public Attributes getAttributes(String name) throws NamingException {
		if (!name.equals("")) {
			throw new NameNotFoundException();
		}
		return myAttrs;
	}

	public Attributes getAttributes(String name, String[] ids) throws NamingException {
		if (!name.equals(""))
			throw new NameNotFoundException();
		Attributes answer = new BasicAttributes(true);
		Attribute target;
		for (int i = 0; i < ids.length; i++) {
			target = myAttrs.get(ids[i]);
			if (target != null) {
				answer.put(target);
			}
		}
		return answer;
	}

	@Validate("required,minLength=3")
	public String getUid() {
		return uid;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	@Validate("required,regexp")
	public String getPassword() {
		return password;
	}

	@Validate("required,regexp")
	public String getPassword1() {
		return password1;
	}

	@Validate("required,email")
	public String getEmail() {
		return email;
	}

	public String getEAAResetPwUUID() {
		return eaaresetpwuuid;
	}

	public String getEAAResetPwDate() {
		return eaaresetpwdate;
	}

	public String getEaahash() {
		return eaahash;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public Hashtable<?, ?> getEnvironment() throws NamingException {

		return null;
	}

	public String getNameInNamespace() throws NamingException {

		return null;
	}

	public NameParser getNameParser(Name arg0) throws NamingException {

		return null;
	}

	public NameParser getNameParser(String arg0) throws NamingException {

		return null;
	}

	public DirContext getSchema(Name arg0) throws NamingException {

		return null;
	}

	public DirContext getSchema(String arg0) throws NamingException {

		return null;
	}

	public DirContext getSchemaClassDefinition(Name arg0) throws NamingException {

		return null;
	}

	public DirContext getSchemaClassDefinition(String arg0) throws NamingException {

		return null;
	}

	public NamingEnumeration<NameClassPair> list(Name arg0) throws NamingException {

		return null;
	}

	public NamingEnumeration<NameClassPair> list(String arg0) throws NamingException {
		return null;
	}

	public NamingEnumeration<Binding> listBindings(Name arg0) throws NamingException {
		return null;
	}

	public NamingEnumeration<Binding> listBindings(String arg0) throws NamingException {
		return null;
	}

	public Object lookup(Name arg0) throws NamingException {
		return null;
	}

	public Object lookup(String arg0) throws NamingException {
		return null;
	}

	public Object lookupLink(Name arg0) throws NamingException {
		return null;
	}

	public Object lookupLink(String arg0) throws NamingException {
		return null;
	}

	public void modifyAttributes(Name arg0, int arg1, Attributes arg2) throws NamingException {

	}

	public void modifyAttributes(Name arg0, ModificationItem[] arg1) throws NamingException {

	}

	public void modifyAttributes(String arg0, int arg1, Attributes arg2) throws NamingException {

	}

	public void modifyAttributes(String arg0, ModificationItem[] arg1) throws NamingException {

	}

	public void rebind(Name arg0, Object arg1) throws NamingException {

	}

	public void rebind(Name arg0, Object arg1, Attributes arg2) throws NamingException {

	}

	public void rebind(String arg0, Object arg1) throws NamingException {

	}

	public void rebind(String arg0, Object arg1, Attributes arg2) throws NamingException {

	}

	public Object removeFromEnvironment(String arg0) throws NamingException {
		return null;
	}

	public void rename(Name arg0, Name arg1) throws NamingException {

	}

	public void rename(String arg0, String arg1) throws NamingException {

	}

	public NamingEnumeration<SearchResult> search(Name arg0, Attributes arg1) throws NamingException {
		return null;
	}

	public NamingEnumeration<SearchResult> search(Name arg0, Attributes arg1, String[] arg2) throws NamingException {
		return null;
	}

	public NamingEnumeration<SearchResult> search(Name arg0, String arg1, Object[] arg2, SearchControls arg3) throws NamingException {
		return null;
	}

	public NamingEnumeration<SearchResult> search(Name arg0, String arg1, SearchControls arg2) throws NamingException {
		return null;
	}

	public NamingEnumeration<SearchResult> search(String arg0, Attributes arg1) throws NamingException {
		return null;
	}

	public NamingEnumeration<SearchResult> search(String arg0, Attributes arg1, String[] arg2) throws NamingException {
		return null;
	}

	public NamingEnumeration<SearchResult> search(String arg0, String arg1, Object[] arg2, SearchControls arg3) throws NamingException {
		return null;
	}

	public NamingEnumeration<SearchResult> search(String arg0, String arg1, SearchControls arg2) throws NamingException {
		return null;
	}

	public void setEaakey(String eaakey) {
		this.eaakey = eaakey;
		myAttrs.put("EAAKey", eaakey);
	}

	public void setEaahash(String eaahash) {
		this.eaahash = eaahash;
		myAttrs.put("EAAHash", eaahash);
	}

	public void setEmail(String email) {

		// convert the email to lower case
		email = email.toLowerCase();

		this.email = email;

		// make sure that email is hashed
		myAttrs.put("mail", EAAHash.getSHA1Hash(email));
	}

	public void setEAAResetPwDate(String eaaresetpwdate) {

		this.eaaresetpwdate = eaaresetpwdate;

		// make sure that email is hashed
		if (eaaresetpwdate != null) {
			myAttrs.put("EAAResetPwDate", eaaresetpwdate);
		}
	}

	public void setEAAResetPwUUID(String eaaresetpwuuid) {
		if (eaaresetpwuuid != null) {
			eaaresetpwuuid = eaaresetpwuuid.toLowerCase();
		}
		this.eaaresetpwuuid = eaaresetpwuuid;
		if (eaaresetpwuuid != null) {
			myAttrs.put("EAAResetPwUUID", eaaresetpwuuid);
		}
	}

	public void setUid(String uid) {
		this.uid = uid;
		myAttrs.put("uid", uid);
	}

	public void setBirthdate(Date birthdate) {
		if (birthdate != null) {
			this.birthdate = birthdate;

			// we have to remove all time information from the date...
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(this.birthdate);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			myAttrs.put("EAABirthdate", EAAHash.getSHA1Hash(new Long(cal.getTime().getTime()).toString()));
		}
	}

	public void unbind(Name arg0) throws NamingException {

	}

	public void unbind(String arg0) throws NamingException {

	}

	public void setPassword(String password) {
		this.password = password;
		myAttrs.put("userPassword", password);
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getEaakey() {
		return eaakey;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
