package service;

import dao.PatientDao;
import dao.UserPatientDao;
import model.UserPatientView;

import java.util.List;

public class UserPatientServiceImpl implements UserPatientService {

	private PatientDao patientDao;
	private UserPatientDao userPatientDao;

	public void setPatientDao(PatientDao patientDao) {
		this.patientDao = patientDao;
	}
	
	public void setUserPatientDao(UserPatientDao userPatientDao) {
		this.userPatientDao = userPatientDao;
	}

	public List<UserPatientView> getUserPatientList() {
		return userPatientDao.getUserPatientList();
	}
	
	public List<UserPatientView> getUserPatientByParam(UserPatientView searchUser) {
		return userPatientDao.getUserPatientByParam(searchUser);
	}
	
	public Integer createPatient(UserPatientView userPatient) {
		Integer patientId = patientDao.createPatient(userPatient);
		userPatient.setPatientId(patientId);
		Integer userPatientId = userPatientDao.createUserPatient(userPatient);
		return userPatientId;
	}
	
	//TODO @budi
	public void updatePatient(UserPatientView patient){
		patientDao.updatePatient(patient);
		//patientDao.backupPatient(patient.getId());
	}
	public void updateUserPatient(UserPatientView userPatient){
		userPatientDao.updateUserPatient(userPatient);
		//userPatientDao.backupUserPatient(userPatient.getId());
	}
	public void changePasswordUserPatient(UserPatientView userPatient){
		userPatientDao.changeUserPatientPassword(userPatient);
	}
	public void verifyUserPatientByVerificationCode (UserPatientView userPatient){
		userPatientDao.verifyUserPatientByVerificationCode(userPatient);
	}
	
	
	public void verifyUserPatient(Integer userId) {
		userPatientDao.verifyUserPatient(userId);
	}
	
	
	
}
