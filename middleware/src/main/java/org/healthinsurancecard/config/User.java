package org.healthinsurancecard.config;

/**
 * User structure in network configuration file
 * 
 * @author Huy Nguyen
 *
 */
public class User {
	
	private String username;
	private String password;
	private String privateKeyPath;
	private String certificatePath;
	
	/**
	 * User constructor 
	 * @param username
	 * @param password
	 * @param privateKeyPath
	 * @param certificatePath
	 */
	public User(String username, String password, String privateKeyPath, String certificatePath) {
		this.username = username;
		this.password = password;
		this.privateKeyPath = privateKeyPath;
		this.certificatePath = certificatePath;
	}
	
	/**
	 * @return username
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * @return password
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * @return privateKeyPath
	 */
	public String getPrivateKeyPath() {
		return this.privateKeyPath;
	}
	
	/**
	 * @return certificatePath
	 */
	public String getCertificatePath() {
		return this.certificatePath;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", privateKeyPath=" + privateKeyPath
				+ ", certificatePath=" + certificatePath + "]";
	}
	
}
