package eu.eurofel.services.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import eu.eurofel.entities.Facility;
import eu.eurofel.services.DaoService;
import eu.eurofel.services.FacilityService;

public class FacilityServiceImpl implements FacilityService {

	private DaoService daoService;

	@SuppressWarnings("unused")
	public void pingFacility(long id) {
		Facility selected = daoService.getFacilityDao().selectFacilityById(
				new Long(id).toString());
		DefaultHttpClient client = new DefaultHttpClient();

		HttpPost post = new HttpPost(selected.getAttributeUpdaterURL());
		try {
			HttpResponse response = client.execute(post);
			selected.setLastPing(new Date());
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
	}

	public void setDaoService(DaoService daoService) {
		this.daoService = daoService;
	}

}
