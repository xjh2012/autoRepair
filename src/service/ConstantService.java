package service;

import model.ConstantView;

import java.util.List;

public interface ConstantService {
	
	public List<ConstantView> getConstantList();
	
	public List<ConstantView> getConstantByParam(ConstantView searchConstant);
	
}
