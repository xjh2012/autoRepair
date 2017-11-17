package service;

import model.UserPatientView;

import java.util.List;

public interface UserPatientService {

	public List<UserPatientView> getUserPatientList();
	public List<UserPatientView> getUserPatientByParam(UserPatientView searchUser);
	public Integer createPatient(UserPatientView userPatient);
	
	//TODO @budi
	public void updatePatient(UserPatientView patient);
	public void updateUserPatient(UserPatientView patient);
	public void changePasswordUserPatient(UserPatientView userPatient);
	public void verifyUserPatientByVerificationCode(UserPatientView userPatient);

	public void verifyUserPatient(Integer userId);
	
}
