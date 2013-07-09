package eu.eurofel.entities.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import eu.eurofel.entities.Facility;
import eu.eurofel.entities.dao.FacilityDao;

public class FacilityDaoImpl extends SqlMapClientDaoSupport implements
		FacilityDao {

	@SuppressWarnings("unchecked")
	public List<Facility> selectAllFacilities() {
		SqlMapClientTemplate template = getSqlMapClientTemplate();
		return template.queryForList("selectAllFacilities");
	}

	public Facility selectFacilityById(String facilityID) {
		SqlMapClientTemplate template = getSqlMapClientTemplate();
		Object objectFacility = template.queryForObject("selectFacilityById",
				facilityID);
		return objectFacility instanceof Facility ? ((Facility) objectFacility)
				: null;
	}

	
	public Long insertFacility(Facility insertFacility) {
		SqlMapClientTemplate template = getSqlMapClientTemplate();
		return (Long)template.insert("insertFacility", insertFacility);
	}

	
	public void deleteFacility(String facilityId) {
		SqlMapClientTemplate template = getSqlMapClientTemplate();
		template.delete("deleteFacility", facilityId);
	}

	
	public void updateFacility(Facility facilityWithNewValues) {
		SqlMapClientTemplate template = getSqlMapClientTemplate();
		template.update("updateFacility", facilityWithNewValues);
	}

	@SuppressWarnings("unchecked")
	
	public List<Facility> selectFacilitiesByName(String facilityName) {
		SqlMapClientTemplate template = getSqlMapClientTemplate();
		return template.queryForList("selectFacilitiesByName",
				facilityName);
	}

}
