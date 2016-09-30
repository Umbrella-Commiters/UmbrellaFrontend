package eu.eurofel.entities.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import eu.eurofel.entities.UserInformation;
import eu.eurofel.entities.dao.UserInformationDao;

public class UserInformationDaoImpl extends SqlMapClientDaoSupport implements
		UserInformationDao {

	public UserInformation selectUserInformationById(String eaahash) {
		SqlMapClientTemplate template = getSqlMapClientTemplate();
		Object objectUserInformation = template.queryForObject("selectUserInformationById",
				eaahash);
		return objectUserInformation instanceof UserInformation ? ((UserInformation) objectUserInformation)
				: null;
	}

	public void insertUserInformation(UserInformation insertUserInformation) {
		SqlMapClientTemplate template = getSqlMapClientTemplate();
		template.insert("insertUserInformation", insertUserInformation);
	}

	public void deleteUserInformation(String eaahash) {
		SqlMapClientTemplate template = getSqlMapClientTemplate();
		template.delete("deleteUserInformation", eaahash);
	}

	public void updateUserInformation(
			UserInformation userInformationWithNewValues) {
		SqlMapClientTemplate template = getSqlMapClientTemplate();
		template.update("updateUserInformation", userInformationWithNewValues);
	}

}
