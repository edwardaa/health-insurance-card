package org.healthinsurancecard.client;

import java.util.Properties;

import org.healthinsurancecard.user.UserContext;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

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
	private UserContext adminContext;
	
	
}
