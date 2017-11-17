package service;

import model.SystemView;

import java.sql.Timestamp;

public interface SystemService {

	public SystemView getLicense();
	
	public Timestamp getCurrentDate();
	
}
