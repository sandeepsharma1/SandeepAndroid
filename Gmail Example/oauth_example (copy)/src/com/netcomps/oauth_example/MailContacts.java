package com.netcomps.oauth_example;

public class MailContacts {
	private String contactName;
	String contactEmail;
	boolean selected = false;
	String code = null;
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return true;
	}
	public boolean isChecked() {
		// TODO Auto-generated method stub
		return true;
	}
	public void setSelected(boolean checked) {
		selected = checked;
		
	}
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}
	public void removeContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
		
	}
	
}
