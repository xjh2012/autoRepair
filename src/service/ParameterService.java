package service;

import model.ParameterView;

import java.util.List;

public interface ParameterService {

	public List<ParameterView> getParameterList();
	
	public List<ParameterView> getParameterByParam(ParameterView searchParameter);
	
	public ParameterView getParameter(ParameterView searchParameter, Integer userId);
	
}
