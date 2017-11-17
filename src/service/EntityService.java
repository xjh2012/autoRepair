package service;

import model.EntitySpecialtyView;
import model.EntityView;
import model.SpecialtyView;

import java.util.List;

public interface EntityService {

	public List<EntityView> getEntityList();
	
	public List<EntityView> getEntityByParam(EntityView searchEntity);
	
	public List<SpecialtyView> getSpecialtyList();
	
	public List<SpecialtyView> getSpecialtyByParam(SpecialtyView searchSpecialty);
	
	public List<EntitySpecialtyView> getEntitySpecialtyList();
	
	public List<EntitySpecialtyView> getEntitySpecialtyByParam(EntitySpecialtyView searchEntitySpecialty);
	
	public List<EntitySpecialtyView> getEntitySpecialtyByPuskesmas(String region);
	
}
