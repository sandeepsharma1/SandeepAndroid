package com.paradigmcreatives.rajiworld.entities;

import java.io.Serializable;

public class Login implements Serializable{
	
	private int userId;
	private String emailAddress;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private boolean activation;
	
	public Login(){}
	
	public Login(int userId, String emailAddress, String firstName,
			String lastName, String username, String password,
			boolean activation) {
		super();
		this.userId = userId;
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.activation = activation;
	}

	/**
	 * @return userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return emailAddress
	 */
	
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return username
	 */
	public String getUsername() {
		///username = getFirstName() + "."+getLastName();
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return activation
	 */
	public boolean isActivation() {
		return activation;
	}

	/**
	 * @param activation
	 *            the activation to set
	 */
	public void setActivation(boolean activation) {
		this.activation = activation;
	}
	


}
