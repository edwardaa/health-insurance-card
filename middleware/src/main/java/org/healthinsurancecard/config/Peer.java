package org.healthinsurancecard.config;

/**
 * Peer structure in configuration file
 * 
 * @author Huy Nguyen
 *
 */
public class Peer {
	
	private String requestURL;
	private String eventURL;
	private String serverHostname;
	private String tlsCACertificatePath;
	
	/**
	 * Peer constructor
	 * 
	 * @param requestURL
	 * @param eventURL
	 * @param serverHostname
	 * @param tlsCACertificatePath
	 */
	public Peer(String requestURL, String eventURL, String serverHostname, String tlsCACertificatePath) {
		this.requestURL = requestURL;
		this.eventURL = eventURL;
		this.serverHostname = serverHostname;
		this.tlsCACertificatePath = tlsCACertificatePath;
	}

	/**
	 * @return requestURL
	 */
	public String getRequestURL() {
		return requestURL;
	}

	/**
	 * @return eventURL
	 */
	public String getEventURL() {
		return eventURL;
	}

	/**
	 * @return serverHostname
	 */
	public String getServerHostname() {
		return serverHostname;
	}

	/**
	 * @return tlsCACertificatePath
	 */
	public String getTlsCACertificatePath() {
		return tlsCACertificatePath;
	}

	@Override
	public String toString() {
		return "Peer [requestURL=" + requestURL + ", eventURL=" + eventURL + ", serverHostname=" + serverHostname
				+ ", tlsCACertificatePath=" + tlsCACertificatePath + "]";
	}
	
}
