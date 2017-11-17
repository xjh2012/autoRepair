package model;

public class ConstantView {

	private Integer id;
	public String idEncrypt;
	private String category;
	private String code;
	private String description;
	private Integer appearanceOrder;
	private Integer isSystem;
	private Integer isActive;
	//TODO @budi
	private Integer isShow;
	private String createdDate;
	private Integer createdBy;
	private String categoryCode;
	
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getAppearanceOrder() {
		return appearanceOrder;
	}
	public void setAppearanceOrder(Integer appearanceOrder) {
		this.appearanceOrder = appearanceOrder;
	}
	public Integer getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	//TODO @budi
	public Integer getIsShow(){
		return isShow;
	}
	public void setIsShow(Integer isShow){
		this.isShow=isShow;
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
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	
}
