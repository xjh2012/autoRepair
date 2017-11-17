package model;

public class FileView {
	
	private Integer id;
	private String idEncrypt;
	private String name;
	private String path;
	private String parPath;
	private Integer size;
	private Integer isDelete;
	private String type;
	private String lastUpdate;
	private String searchTime;
	private String createdDate;
	private Integer createdBy;
	
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIdEncrypt() {
		return idEncrypt;
	}
	public void setIdEncrypt(String idEncrypt) {
		this.idEncrypt = idEncrypt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public void setParPath(String parPath) {
		this.parPath = parPath;
	}
	public String getParPath() {
		return parPath;
	}
	public void setSearchTime(String searchTime) {
		this.searchTime = searchTime;
	}
	public String getSearchTime() {
		return searchTime;
	}
	
	
	
	
}
