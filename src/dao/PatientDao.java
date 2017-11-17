package dao;

import model.UserPatientView;

public interface PatientDao {
	
	public Integer createPatient(UserPatientView patient);
	//TODO @budi
	public void updatePatient(UserPatientView patient);
	public void backupPatient(Integer id);
	
}
