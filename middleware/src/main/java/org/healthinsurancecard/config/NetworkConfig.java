package org.healthinsurancecard.config;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

/**
 * NetworkConfig structure
 * 
 * @author Huy Nguyen
 *
 */
public class NetworkConfig {

	private Orderer orderer;
	private List<Organization> organizations;
	private Chaincode chaincode;
	private Channel channel;
	private User useradmin;
	
	/**
	 * NetworkConfig constructor
	 * 
	 * @param configPath
	 */
	public NetworkConfig(String configPath) {
		Gson gson = new Gson();
		NetworkConfig config = gson.fromJson(parseToJavaObject(configPath), NetworkConfig.class);
		this.orderer = config.getOrderer();
		this.organizations = config.getOrganizations();
		this.chaincode = config.getChaincode();
		this.channel = config.getChannel();
		this.useradmin = config.getUserAdmin();
	}
	
	/**
	 * NetworkConfig constructor
	 * 
	 * @param orderer
	 * @param organizations
	 * @param chaincode
	 * @param channel
	 * @param userAdmin
	 */
	public NetworkConfig(Orderer orderer, List<Organization> organizations, Chaincode chaincode, Channel channel,
			User userAdmin) {
		this.orderer = orderer;
		this.organizations = organizations;
		this.chaincode = chaincode;
		this.channel = channel;
		this.useradmin = userAdmin;
	}
	
	public String parseToJavaObject(String configPath) {
		StringBuilder result = new StringBuilder("");

		//Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(configPath).getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return result.toString();
	}

	/**
	 * @return orderer
	 */
	public Orderer getOrderer() {
		return orderer;
	}

	/**
	 * @return organizations
	 */
	public List<Organization> getOrganizations() {
		return organizations;
	}

	/**
	 * @return chaincode
	 */
	public Chaincode getChaincode() {
		return chaincode;
	}

	/**
	 * @return channel
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * @return userAdmin
	 */
	public User getUserAdmin() {
		return useradmin;
	}

	@Override
	public String toString() {
		return "NetworkConfig [orderer=" + orderer + ", organizations=" + organizations + ", chaincode=" + chaincode
				+ ", channel=" + channel + ", userAdmin=" + useradmin + "]";
	}
	
}
