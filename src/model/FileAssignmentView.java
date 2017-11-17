package model;

public class FileAssignmentView {
	
	//file_assignment table
	private Integer id;
	private String idEncrypt;
	private Integer fileId;
	private String fileIdEncrypt;
	private Integer assignmentId;
	private String assignmentIdEncrypt;
	private String name;
	private String type;
	private String input;
	private String output;
	private String expectedOutput;
	private String lastUpdate;
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
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	public Integer getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(Integer assignmentId) {
		this.assignmentId = assignmentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getExpectedOutput() {
		return expectedOutput;
	}
	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
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
	public String getFileIdEncrypt() {
		return fileIdEncrypt;
	}
	public void setFileIdEncrypt(String fileIdEncrypt) {
		this.fileIdEncrypt = fileIdEncrypt;
	}
	public String getAssignmentIdEncrypt() {
		return assignmentIdEncrypt;
	}
	public void setAssignmentIdEncrypt(String assignmentIdEncrypt) {
		this.assignmentIdEncrypt = assignmentIdEncrypt;
	}
	

	

	
	
	
}
