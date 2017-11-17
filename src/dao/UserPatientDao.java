package dao;

import model.UserPatientView;

import java.util.List;

public interface UserPatientDao {
	
	public List<UserPatientView> getUserPatientList();
	
	public List<UserPatientView> getUserPatientByParam(UserPatientView searchUser);
	
	public Integer createUserPatient(UserPatientView userPatient);
	//TODO @budi
	
	public void backupUserPatient(Integer userId);

	public void updateUserPatient(UserPatientView userPatient);
	
	public void verifyUserPatient(Integer userId);
	
	public void verifyUserPatientByVerificationCode(UserPatientView userPatient);

	public void changeUserPatientPassword(UserPatientView userPatient);



}
