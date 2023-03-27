package com.sud.aws_appconfig_demo.model;

public class OpcoConfiguration {

	String partnerId;
	String appId;
	String opcoName;
	String opcoLanguage;
	String opcoDomain;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getOpcoName() {
		return opcoName;
	}
	public void setOpcoName(String opcoName) {
		this.opcoName = opcoName;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getOpcoLanguage() {
		return opcoLanguage;
	}
	public void setOpcoLanguage(String opcoLanguage) {
		this.opcoLanguage = opcoLanguage;
	}
	public String getOpcoDomain() {
		return opcoDomain;
	}
	public void setOpcoDomain(String opcoDomain) {
		this.opcoDomain = opcoDomain;
	}

}
