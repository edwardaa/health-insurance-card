package org.healthinsurancecard.config;

import java.util.List;

/**
 * Organization structure in configuration file
 * 
 * @author Huy Nguyen
 *
 */
public class Organization {

	private String name;
	private String mspId;
	private User useradmin;
	private CA ca;
	private List<Peer> peers;
	
	/**
	 * Organization constructor
	 * 
	 * @param name
	 * @param mspId
	 * @param userAdmin
	 * @param ca
	 * @param peers
	 */
	public Organization(String name, String mspId, User userAdmin, CA ca, List<Peer> peers) {
		this.name = name;
		this.mspId = mspId;
		this.useradmin = userAdmin;
		this.ca = ca;
		this.peers = peers;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return mspId
	 */
	public String getMspId() {
		return mspId;
	}

	/**
	 * @return user admin object
	 */
	public User getUserAdmin() {
		return useradmin;
	}

	/**
	 * @return ca object
	 */
	public CA getCa() {
		return ca;
	}

	/**
	 * @return list of peers
	 */
	public List<Peer> getPeers() {
		return peers;
	}

	@Override
	public String toString() {
		return "Organization [name=" + name + ", mspid=" + mspId + ", useradmin=" + useradmin + ", ca=" + ca
				+ ", peers=" + peers + "]";
	}
	
}
