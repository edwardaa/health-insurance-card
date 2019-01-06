package org.healthinsurancecard.client;

import java.util.Properties;

import org.healthinsurancecard.user.UserContext;
import org.healthinsurancecard.util.Utils;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

/**
 * CA Client wrapper class
 * 
 * @author Huy Nguyen
 *
 */
public class CAClient {

	private String caURL;


	private Properties caProperties;
	private HFCAClient instance;
	private UserContext adminUserContext;
	
	public String getCaURL() {
		return caURL;
	}

	public Properties getCaProperties() {
		return caProperties;
	}

	public HFCAClient getInstance() {
		return instance;
	}
	
	public UserContext getAdminUserContext() {
		return adminUserContext;
	}
	
	public void setAdminUserContext(UserContext adminUserContext) {
		this.adminUserContext = adminUserContext;
	}
	
	public void init() throws Exception {
		CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
		instance = HFCAClient.createNewInstance(caURL, caProperties);
		instance.setCryptoSuite(cryptoSuite);
	}
	
	public CAClient(String caURL, Properties caProperties) throws Exception {
		this.caURL = caURL;
		this.caProperties = caProperties;
		init();
	}
	
	/**
	 * Enroll admin user
	 * 
	 * @param username
	 * @param password
	 * @return user context with enrolled admin user
	 * @throws Exception
	 */
	public UserContext enrollAdminUser(String username, String password) throws Exception {
		UserContext userContext = Utils.readUserContext(adminUserContext.getAffiliation(), username);
		if (userContext != null) {
			return userContext;
		}
		Enrollment adminEnrollment = instance.enroll(username, password);
		adminUserContext.setEnrollment(adminEnrollment);
		Utils.writeUserContext(adminUserContext);
		return adminUserContext;
	}
	
	/**
	 * Register user
	 * 
	 * @param username
	 * @param organization
	 * @return password of registerd user
	 * @throws Exception
	 */
	public String registerUser(String username, String organization) throws Exception {
		UserContext userContext = Utils.readUserContext(adminUserContext.getAffiliation(), username);
		if (userContext != null) {
			return null;
		}
		RegistrationRequest request = new RegistrationRequest(username, organization);
		String password = instance.register(request, adminUserContext);
		return password;
	}
	
	/**
	 * Enroll normal user
	 * @param user normal user
	 * @param password
	 * @return user context with enrolled user
	 * @throws Exception
	 */
	public UserContext enrollUser(UserContext user, String password) throws Exception {
		UserContext userContext = Utils.readUserContext(adminUserContext.getAffiliation(), user.getName());
		if (userContext != null) {
			return userContext;
		}
		Enrollment enrollment = instance.enroll(user.getName(), password);
		user.setEnrollment(enrollment);
		Utils.writeUserContext(user);
		return user;
	}
}
