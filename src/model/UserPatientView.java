package model;

public final class UserPatientView {

	private Integer id;
	private String idEncrypt;
	private String userName;
	private String password;
	private Integer patientId;
	private String createdDate;
	private Integer isVerified;
	private String verifiedDate;
	//TODO 
	private String fullName;
	private String firstName;
	private String lastName;
	private String gender;
	private String birthDate;
	private String IdNo;
	private String BpjsNo;
	private String region;
	private String regionDescription;
	private String handphone;
	private String email;
	private String roleConstant;
	//TODO db update
	// New User Patient Master Fields 
	private String verificationCode;
	private String stsActive;
	private String lastUpdate;
	private String updater;
	// Additional
	private String imageProfile;
	
	//TODO @budi
	private String birthPlace;
	private String cityCode;
	private String cityName;
	private String fullAddress;
	private String streetName;
	private String rtNo;
	private String rwNo;
	private String villageName;
	private String districtName;
	private Integer postalCode;
	private String province;
	private String status;
	private String occupation;
	private String entityDefault;
	private String entityDefaultName;
	private Integer height;
	private Integer weight;
	private String bloodType;
	private String bloodRhesus;


	
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
	//TODO @budi add fullName
	public String getFullName(){
		return fullName;
	}
	public void setFullName(String fullName){
		this.fullName = fullName;
	}
	
	public String getGender(){
		return gender;
	}
	public void setGender(String gender){
		this.gender=gender;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(Integer isVerified) {
		this.isVerified = isVerified;
	}
	public String getVerifiedDate() {
		return verifiedDate;
	}
	public void setVerifiedDate(String verifiedDate) {
		this.verifiedDate = verifiedDate;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getIdNo() {
		return IdNo;
	}
	public void setIdNo(String idNo) {
		IdNo = idNo;
	}
	public String getBpjsNo() {
		return BpjsNo;
	}
	public void setBpjsNo(String bpjsNo) {
		BpjsNo = bpjsNo;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRegionDescription() {
		return regionDescription;
	}
	public void setRegionDescription(String regionDescription) {
		this.regionDescription = regionDescription;
	}
	public String getHandphone() {
		return handphone;
	}
	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRoleConstant() {
		return roleConstant;
	}
	public void setRoleConstant(String roleConstant) {
		this.roleConstant = roleConstant;
	}
	
	//TODO @budi
	public String getVerificationCode(){
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode){
		this.verificationCode = verificationCode;
	}
	
	
	//TODO @budi
	public String getLastUpdate(){
		return lastUpdate;
		
	}
	public void setLastUpdate(String lastUpdate){
		this.lastUpdate =lastUpdate;
	}
	// New User Patient Master Fields
	public String getUpdater(){
		return updater;
	}
	
	public void setUpdater(String updater){
		this.updater = updater;
	}
	
	
	/*//TODO @budi

	
	private Integer postalCode;
	private String province;
	private String status;
	private String occupation;
	private String entityDefaultCode;
	private String entityDefaultName;
	private Integer height;
	private Integer weight;
	private String bloodType;
	private String bloodRhesus;
	*/
	public String getBirthPlace()
	{
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace){
		this.birthPlace = birthPlace;
	}
	
	public String getCityCode(){
		return cityCode;
	}
	public void setCityCode(String cityCode){
		this.cityCode = cityCode;
	}
	
	public String getCityName(){
		return cityName;
	}
	public void setCityName(String cityName){
		this.cityName = cityName;
	}
	
	public String getFullAddress(){
		return fullAddress;
	}
	public void setFullAddress(String fullAddress){
		this.fullAddress = fullAddress;
	}
	
	public String getStreetName(){
		return streetName;
	}
	public void setStreetName(String streetName){
		this.streetName = streetName;
	}
	
	public String getRtNo(){
		return rtNo;
	}
	public void setRtNo(String rtNo){
		this.rtNo = rtNo;
	}
	
	public String getRwNo(){
		return rwNo;
	}
	
	public void setRwNo(String rwNo){
		this.rwNo = rwNo;
	}
	
	public String getVillageName(){
		return villageName;
	}
	public void setVillageName(String villageName){
		this.villageName = villageName;
	}
	
	public String getDistrictName(){
		return districtName;
	}
	public void setDistrictName(String districtName){
		this.districtName = districtName;
	}
	
	public String getStatus(){
		return status;
	}
	public void setStatus(String status) {
		// TODO Auto-generated method stub
		this.status = status;
	}
	public String getOccupation(){
		return occupation;
	}
	public void setOccupation(String occupation){
		this.occupation = occupation;
	}
	public String getEntityDefault(){
		return entityDefault;
	}
	public void setEntityDefault( String entityDefault){
		this.entityDefault = entityDefault;
	}

}
