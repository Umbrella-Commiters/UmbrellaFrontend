package eu.eurofel.entities;

import java.io.Serializable;
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

import eu.eurofel.util.Constants;

public class BridgeFederation implements DirContext, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5177773105874705629L;

	private String bridgeFederationSrc;

	private String bridgeFederationUid;
	
	private String bridgeFederationUmbrellaUsername;

	private String bridgeUmbrellaUid;
	
	private String uid;

	String dn;

	String id;

	Attributes myAttrs = new BasicAttributes(true);

	Attribute oc = new BasicAttribute("objectclass");

	Attribute ouSet = new BasicAttribute("ou");

	public BridgeFederation() {
		oc.add("BridgeFederation");
		oc.add("top");
		ouSet.add("bridge");
		ouSet.add(Constants.BRIDGE_DN);
		myAttrs.put(oc);
		myAttrs.put(ouSet);

	}

	public String getBridgeFederationSrc() {
		return bridgeFederationSrc;
	}

	public void setBridgeFederationSrc(String bridgeFederationSrc) {
		this.bridgeFederationSrc = bridgeFederationSrc;
		myAttrs.put("BridgeFederationSrc", bridgeFederationSrc);
	}

	public String getBridgeFederationUid() {
		return bridgeFederationUid;
	}

	public void setBridgeFederationUid(String bridgeFederationUid) {
		this.bridgeFederationUid = bridgeFederationUid;
		myAttrs.put("BridgeFederationUID", bridgeFederationUid);
	}

	public String getBridgeUmbrellaUid() {
		return bridgeUmbrellaUid;
	}

	public void setBridgeUmbrellaUid(String bridgeUmbrellaUid) {
		this.bridgeUmbrellaUid = bridgeUmbrellaUid;
		myAttrs.put("BridgeFederationUmbrellaUID", bridgeUmbrellaUid);
	}


	public String getBridgeFederationUmbrellaUsername() {
		return bridgeFederationUmbrellaUsername;
	}

	public void setBridgeFederationUmbrellaUsername(String bridgeUmbrellaUsername) {
		this.bridgeFederationUmbrellaUsername = bridgeUmbrellaUsername;
		myAttrs.put("BridgeFederationUmbrellaUsername", bridgeUmbrellaUsername);
	}	
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
		myAttrs.put("uid", uid);
	}

	@Override
	public Object addToEnvironment(String arg0, Object arg1)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bind(Name arg0, Object arg1) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void bind(String arg0, Object arg1) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public Name composeName(Name arg0, Name arg1) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String composeName(String arg0, String arg1) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Context createSubcontext(Name arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Context createSubcontext(String arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroySubcontext(Name arg0) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroySubcontext(String arg0) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public Hashtable<?, ?> getEnvironment() throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNameInNamespace() throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NameParser getNameParser(Name arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NameParser getNameParser(String arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<NameClassPair> list(Name arg0)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<NameClassPair> list(String arg0)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<Binding> listBindings(Name arg0)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<Binding> listBindings(String arg0)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object lookup(Name arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object lookup(String arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object lookupLink(Name arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object lookupLink(String arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rebind(Name arg0, Object arg1) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rebind(String arg0, Object arg1) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object removeFromEnvironment(String arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rename(Name arg0, Name arg1) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rename(String arg0, String arg1) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unbind(Name arg0) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unbind(String arg0) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void bind(Name arg0, Object arg1, Attributes arg2)
			throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void bind(String arg0, Object arg1, Attributes arg2)
			throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public DirContext createSubcontext(Name arg0, Attributes arg1)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirContext createSubcontext(String arg0, Attributes arg1)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attributes getAttributes(Name name) throws NamingException {

		return getAttributes(name.toString());
	}

	@Override
	public Attributes getAttributes(String name) throws NamingException {
		if (!name.equals("")) {
			throw new NameNotFoundException();
		}
		return myAttrs;
	}

	@Override
	public Attributes getAttributes(Name name, String[] ids)
			throws NamingException {

		return getAttributes(name.toString(), ids);
	}

	@Override
	public Attributes getAttributes(String name, String[] ids)
			throws NamingException {
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

	@Override
	public DirContext getSchema(Name arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirContext getSchema(String arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirContext getSchemaClassDefinition(Name arg0)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirContext getSchemaClassDefinition(String arg0)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modifyAttributes(Name arg0, ModificationItem[] arg1)
			throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyAttributes(String arg0, ModificationItem[] arg1)
			throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyAttributes(Name arg0, int arg1, Attributes arg2)
			throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyAttributes(String arg0, int arg1, Attributes arg2)
			throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rebind(Name arg0, Object arg1, Attributes arg2)
			throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rebind(String arg0, Object arg1, Attributes arg2)
			throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public NamingEnumeration<SearchResult> search(Name arg0, Attributes arg1)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(String arg0, Attributes arg1)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(Name arg0, Attributes arg1,
			String[] arg2) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(String arg0, Attributes arg1,
			String[] arg2) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(Name arg0, String arg1,
			SearchControls arg2) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(String arg0, String arg1,
			SearchControls arg2) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(Name arg0, String arg1,
			Object[] arg2, SearchControls arg3) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(String arg0, String arg1,
			Object[] arg2, SearchControls arg3) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

}
