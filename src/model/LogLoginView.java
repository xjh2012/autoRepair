package model;

public class LogLoginView {

	private Integer id;
	private String sessionId;
	private String userName;
	private String ipAddress;
	private String createdDate;
	private String statusConstant;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getStatusConstant() {
		return statusConstant;
	}
	public void setStatusConstant(String statusConstant) {
		this.statusConstant = statusConstant;
	}
	
}
