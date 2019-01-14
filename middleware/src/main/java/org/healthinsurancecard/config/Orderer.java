package org.healthinsurancecard.config;

/**
 * Order structure in configuration file
 * 
 * @author Huy Nguyen
 *
 */
public class Orderer {
	
	private String mspId;
	private String url;
	private String serverHostname;
	private String tlsCertificatePath;
	
	/**
	 * Orderer constructor
	 * 
	 * @param mspId
	 * @param url
	 * @param serverHostname
	 * @param tlsCertificatePath
	 */
	public Orderer(String mspId, String url, String serverHostname, String tlsCertificatePath) {
		super();
		this.mspId = mspId;
		this.url = url;
		this.serverHostname = serverHostname;
		this.tlsCertificatePath = tlsCertificatePath;
	}
	
	/**
	 * @return mspId
	 */
	public String getMspId() {
		return mspId;
	}
	
	/**
	 * @return url
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * @return serverHostname
	 */
	public String getServerHostname() {
		return serverHostname;
	}
	
	/**
	 * @return tlsCertificatePath
	 */
	public String getTlsCertificatePath() {
		return tlsCertificatePath;
	}

	@Override
	public String toString() {
		return "Orderer [mspId=" + mspId + ", url=" + url + ", serverHostname=" + serverHostname
				+ ", tlsCertificatePath=" + tlsCertificatePath + "]";
	}
	
}
