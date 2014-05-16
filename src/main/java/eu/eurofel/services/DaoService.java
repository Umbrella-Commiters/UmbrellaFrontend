package eu.eurofel.services;

import org.springframework.stereotype.Service;

import eu.eurofel.entities.dao.FacilityDao;

@Service
public interface DaoService {

	FacilityDao getFacilityDao();
}
