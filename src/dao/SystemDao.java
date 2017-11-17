package dao;

import model.SystemView;

import java.sql.Timestamp;

public interface SystemDao {

	public SystemView getLicense();
	
	public Timestamp getCurrentDate();
	
}
