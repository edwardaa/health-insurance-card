package org.healthinsurancecard.config;

/**
 * CA structure in network configuration file
 * 
 * @author Huy Nguyen
 *
 */
public class CA {
	
	private String name;
	private String url;
	
	/**
	 * CA constructor
	 * 
	 * @param name
	 * @param url
	 */
	public CA(String name, String url) {
		this.name = name;
		this.url = url;
	}
	
	/**
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return url
	 */
	public String getURL() {
		return this.url;
	}

	@Override
	public String toString() {
		return "CA [name=" + name + ", url=" + url + "]";
	}
	
}
