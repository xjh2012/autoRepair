package service;

import model.LogLoginView;
import model.LogMenuView;

import java.util.List;

public interface LogService {

	public List<LogLoginView> getLogLoginList();
	
	public List<LogLoginView> getLogLoginByParam(LogLoginView searchLogLogin);
	
	public void insertLogLogin(String sessionId, String userName, String ipAddress, String statusConstant);	
	
	public List<LogMenuView> getLogMenuList();
	
	public List<LogMenuView> getLogMenuByParam(LogMenuView searchLogMenu);
	
	public void insertLogMenu(String sessionId, String menuConstant);

}
