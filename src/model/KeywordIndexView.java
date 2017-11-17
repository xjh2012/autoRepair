package model;

public class KeywordIndexView {
	private Integer id;
	private String idEncrypt;
	private String keywordName;
	private String keywordId;
	private Integer found;
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
	public String getKeywordId() {
		return keywordId;
	}
	public void setKeywordId(String keywordId) {
		this.keywordId = keywordId;
	}
	public Integer getFound() {
		return found;
	}
	public void setFound(Integer found) {
		this.found = found;
	}
	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}
	public String getKeywordName() {
		return keywordName;
	}
	


	
	
	
}
