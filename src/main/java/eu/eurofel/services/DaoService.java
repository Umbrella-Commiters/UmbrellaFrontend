package eu.eurofel.services;

import org.springframework.stereotype.Service;

import eu.eurofel.entities.dao.FacilityDao;
import eu.eurofel.entities.dao.UserInformationDao;

@Service
public interface DaoService {

	FacilityDao getFacilityDao();
	
	UserInformationDao getUserInformationDao();
}
