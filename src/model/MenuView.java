package model;

public final class MenuView {

	private Integer id;
	private Integer categoryId;
	private String name;
	private String description;
	private String link;
	private String icon;
	private Integer appearanceOrder;
	private String menuConstant;
	private Integer isActive;
	private Integer isVisible;
	private String categoryDescription;
	private Integer categoryAppearanceOrder;
	private Integer categoryIsActive;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getAppearanceOrder() {
		return appearanceOrder;
	}
	public void setAppearanceOrder(Integer appearanceOrder) {
		this.appearanceOrder = appearanceOrder;
	}
	public String getMenuConstant() {
		return menuConstant;
	}
	public void setMenuConstant(String menuConstant) {
		this.menuConstant = menuConstant;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	public Integer getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	public Integer getCategoryAppearanceOrder() {
		return categoryAppearanceOrder;
	}
	public void setCategoryAppearanceOrder(Integer categoryAppearanceOrder) {
		this.categoryAppearanceOrder = categoryAppearanceOrder;
	}
	public Integer getCategoryIsActive() {
		return categoryIsActive;
	}
	public void setCategoryIsActive(Integer categoryIsActive) {
		this.categoryIsActive = categoryIsActive;
	}
	
}
