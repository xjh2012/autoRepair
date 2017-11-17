package model;

public class LocationView {
	private Integer id;
	private String regionName;
	private String cityCode;
	private String cityName;
	private String latitude;
	private String longitude;
	private Integer isActive;
	private String lastUpdate;
	private String updater;
	
	
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getRegionName(){
		return regionName;
	}
	
	public void setRegionName(String regionName){
		this.regionName=regionName;
	}
	
	public String getCityCode(){
		return cityCode;
	}
	
	public void setCityCode(String cityCode){
		this.cityCode=cityCode;
	}
	
	public String getCityName(){
		return cityName;
	}
	
	public void setCityName(String cityName){
		this.cityName=cityName;
	}
	
	public String getLatitude(){
		return latitude;
	}
	
	public void setLatitude(String latitude){
		this.latitude=latitude;
	}
	
	public String getLongitude(){
		return longitude;
	}
	
	public void setLongitude(String longitude){
		this.longitude=longitude;
	}
	
	public Integer getIsActive(){
		return isActive;
	}
	
	public void setIsActive(Integer isActive){
		this.isActive = isActive;
	}
	
	public String getLastUpdate(){
		return lastUpdate;
	}
	
	public void setLastUpdate(String lastUpdate){
		this.lastUpdate=lastUpdate;
	}
	
	public String getUpdater(){
		return updater;
	}
	
	public void setUpdater(String updater){
		this.updater=updater;
	}
	

}
