package eu.eurofel.entities.dao;

import eu.eurofel.entities.UserInformation;

public interface UserInformationDao {

	public UserInformation selectUserInformationById(String eaahash);

	public void insertUserInformation(UserInformation insertUserInformation);

	public void deleteUserInformation(String eaahash);

	public void updateUserInformation(UserInformation userInformationWithNewValues);
	
}
