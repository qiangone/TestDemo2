package com.capgemini.wdapp.model;


public class Shop {

	private Integer id;
	private String name;
	private String description;
	private Integer sellerId;
	private String token;
	private Float transportExpense;
	private String logoImg;
	private String backgImg;
	private String weChatAppKey;
	private String weChatAppSecret;
	
	private String address;
	private String postCode;
	private String contact;
	private String telephone;
	private String province ;
	private String  city ;
	private String  region;
	
	
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}
	/**
	 * @param postCode the postCode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the sellerId
	 */
	public Integer getSellerId() {
		return sellerId;
	}
	/**
	 * @param sellerId the sellerId to set
	 */
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	
	/**
	 * @return the transportExpense
	 */
	public Float getTransportExpense() {
		return transportExpense;
	}
	/**
	 * @param transportExpense the transportExpense to set
	 */
	public void setTransportExpense(Float transportExpense) {
		this.transportExpense = transportExpense;
	}
	/**
	 * @return the logoImg
	 */
	public String getLogoImg() {
		return logoImg;
	}
	/**
	 * @param logoImg the logoImg to set
	 */
	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}
	/**
	 * @return the backgImg
	 */
	public String getBackgImg() {
		return backgImg;
	}
	

	
	/**
	 * @param backgImg the backgImg to set
	 */
	public void setBackgImg(String backgImg) {
		this.backgImg = backgImg;
	}
	public String getWeChatAppKey() {
		return weChatAppKey;
	}
	public void setWeChatAppKey(String weChatAppKey) {
		this.weChatAppKey = weChatAppKey;
	}
	public String getWeChatAppSecret() {
		return weChatAppSecret;
	}
	public void setWeChatAppSecret(String weChatAppSecret) {
		this.weChatAppSecret = weChatAppSecret;
	}
	
	
}
