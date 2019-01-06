package org.healthinsurancecard.user;

import java.io.Serializable;
import java.security.PrivateKey;

import org.hyperledger.fabric.sdk.Enrollment;

/**
 * CA Enrollment class
 * 
 * @author Huy Nguyen
 *
 */
public class CAEnrollment implements Enrollment, Serializable {

	private static final long serialVersionUID = 1L;
	private PrivateKey key;
	private String cert;
	
	/**
	 * CAEnrollment constructor
	 * @param key private key
	 * @param cert digital certificate
	 */
	public CAEnrollment(PrivateKey key, String cert) {
		this.key = key;
		this.cert = cert;
	}

	@Override
	public PrivateKey getKey() {
		return key;
	}

	@Override
	public String getCert() {
		return cert;
	}

}
