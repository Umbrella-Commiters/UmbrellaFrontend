package eu.eurofel.entities.dao;

import java.util.List;

import eu.eurofel.entities.Facility;

public interface FacilityDao {
	public List<Facility> selectAllFacilities();

	public Facility selectFacilityById(String facilityID);

	public List<Facility> selectFacilitiesByName(String facilityID);

	public Long insertFacility(Facility insertFacility);

	public void deleteFacility(String facilityId);

	public void updateFacility(Facility facilityWithNewValues);
}
