package model;

import java.util.Comparator;

public class KeywordView {
	private Integer id;
	private String idEncrypt;
	private String keyword;
	private String type;
	private String path;
	private Integer found;
	private String fileId;
	private String fileIdEncrypt;
	private Integer startPosition;
	private Integer endPosition;
	private String createdDate;
	private Integer createdBy;
	
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
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getFound() {
		return found;
	}
	public void setFound(Integer found) {
		this.found = found;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	
	public void setStartPosition(Integer startPosition) {
		this.startPosition = startPosition;
	}
	public Integer getStartPosition() {
		return startPosition;
	}

	public void setEndPosition(Integer endPosition) {
		this.endPosition = endPosition;
	}
	public Integer getEndPosition() {
		return endPosition;
	}

	public void setFileIdEncrypt(String fileIdEncrypt) {
		this.fileIdEncrypt = fileIdEncrypt;
	}
	public String getFileIdEncrypt() {
		return fileIdEncrypt;
	}

	//updated @budi 20160601 add dynamic sort
	public static Comparator<KeywordView> KeywordComparator = new Comparator<KeywordView>(){
		public int compare( KeywordView object1, KeywordView object2) {
			int flag=0;
			String existedKeyword = object1.getKeyword() +" "+  object1.getPath();
			String newKeyword = object2.getKeyword() +" "+  object2.getPath();
			
			return existedKeyword.compareTo(newKeyword);
		}

		
	
	};
}

