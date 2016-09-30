package eu.eurofel.services.impl;

import java.util.Collection;

import eu.eurofel.entities.Address;
import eu.eurofel.entities.Facility;
import eu.eurofel.services.DisseminationService;

public class DisseminationServiceImpl implements DisseminationService {

//	private DaoService daoService;

	public void disseminateInformation(Address address) {
		// lets show all properties

//		FacilityDao facilityDao = daoService.getFacilityDao();
//
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//
//		List<Facility> allFacilities = facilityDao.selectAllFacilities();
//		for (Facility aFacility : allFacilities) {
//			if (aFacility.isEnabled()) {
//				try {
//					HttpPost post = new HttpPost(aFacility.getAttributeUpdaterURL());
//					ChallengeResponse cr = new ChallengeResponseImpl();
//					String challenge = cr.generateChallenge();
////					System.out.println(challenge + " | " + address.getEaahash());
//					List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//					formparams.add(new BasicNameValuePair("EAAChallenge", challenge));
//					formparams.add(new BasicNameValuePair("EAAHash", address.getEaahash()));
//					post.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
//					HttpResponse response = httpclient.execute(post);
//					String resp = IOUtils.toString(response.getEntity().getContent());
//					String rand = resp.split("\\|")[0];
//					String clientresult = resp.split("\\|")[1];
//
//					String result = cr.calculate(address.getEaakey(), challenge, rand);
//
//					// if the results are the same
//					if (result.equals(clientresult)) {
//						BeanInfo bi = Introspector.getBeanInfo(address.getClass());
//						PropertyDescriptor[] pd = bi.getPropertyDescriptors();
//
//						formparams = new ArrayList<NameValuePair>();
//						formparams.add(new BasicNameValuePair("EAAResult", result));
//						for (int i = 0; i < pd.length; i++) {
//							Method readMethod = pd[i].getReadMethod();
//							String value = readMethod.invoke(address, (Object[]) null).toString();
//
//							formparams.add(new BasicNameValuePair(pd[i].getDisplayName(), value));
//						}
//
//						post.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
//						// Check code
//						response = httpclient.execute(post);
//						resp = IOUtils.toString(response.getEntity().getContent());
//					}
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}

	public void disseminateInformation(Address address, Collection<Facility> facilities) {
		// TODO Auto-generated method stub

	}

//	public void setDaoService(DaoService daoService) {
//		this.daoService = daoService;
//	}

}
