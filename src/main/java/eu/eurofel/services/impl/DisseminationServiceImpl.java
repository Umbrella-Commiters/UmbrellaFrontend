package eu.eurofel.services.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import eu.eurofel.entities.Address;
import eu.eurofel.entities.Facility;
import eu.eurofel.entities.dao.FacilityDao;
import eu.eurofel.services.DaoService;
import eu.eurofel.services.DisseminationService;
import eu.eurofel.util.ChallengeResponse;
import eu.eurofel.util.ChallengeResponseImpl;

public class DisseminationServiceImpl implements DisseminationService {

	private DaoService daoService;

	public void disseminateInformation(Address address) {
		// lets show all properties

		FacilityDao facilityDao = daoService.getFacilityDao();

		DefaultHttpClient httpclient = new DefaultHttpClient();

		List<Facility> allFacilities = facilityDao.selectAllFacilities();
		for (Facility aFacility : allFacilities) {
			if (aFacility.isEnabled()) {
				try {
					HttpPost post = new HttpPost(aFacility.getAttributeUpdaterURL());
					ChallengeResponse cr = new ChallengeResponseImpl();
					String challenge = cr.generateChallenge();
					System.out.println(challenge + " | " + address.getEaahash());
					List<NameValuePair> formparams = new ArrayList<NameValuePair>();
					formparams.add(new BasicNameValuePair("EAAChallenge", challenge));
					formparams.add(new BasicNameValuePair("EAAHash", address.getEaahash()));
					post.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
					HttpResponse response = httpclient.execute(post);
					String resp = IOUtils.toString(response.getEntity().getContent());
					String rand = resp.split("\\|")[0];
					String clientresult = resp.split("\\|")[1];

					String result = cr.calculate(address.getEaakey(), challenge, rand);

					// if the results are the same
					if (result.equals(clientresult)) {
						BeanInfo bi = Introspector.getBeanInfo(address.getClass());
						PropertyDescriptor[] pd = bi.getPropertyDescriptors();

						formparams = new ArrayList<NameValuePair>();
						formparams.add(new BasicNameValuePair("EAAResult", result));
						for (int i = 0; i < pd.length; i++) {
							Method readMethod = pd[i].getReadMethod();
							String value = readMethod.invoke(address, (Object[]) null).toString();

							formparams.add(new BasicNameValuePair(pd[i].getDisplayName(), value));
						}

						post.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
						// Check code
						response = httpclient.execute(post);
						resp = IOUtils.toString(response.getEntity().getContent());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void disseminateInformation(Address address, Collection<Facility> facilities) {
		// TODO Auto-generated method stub

	}

	public void setDaoService(DaoService daoService) {
		this.daoService = daoService;
	}

}
