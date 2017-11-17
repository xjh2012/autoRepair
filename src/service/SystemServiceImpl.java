package service;

import dao.SystemDao;
import model.SystemView;

import java.sql.Timestamp;

public class SystemServiceImpl implements SystemService {

	private SystemDao systemDao;
	
	public void setSystemDao(SystemDao systemDao) {
		this.systemDao = systemDao;
	}

	public SystemView getLicense(){
		return systemDao.getLicense();
	}
	
	public Timestamp getCurrentDate(){
		return systemDao.getCurrentDate();
	}
	
}