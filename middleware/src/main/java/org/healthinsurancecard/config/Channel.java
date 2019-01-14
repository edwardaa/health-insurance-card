package org.healthinsurancecard.config;

/**
 * Channel structure in network configuration file
 * 
 * @author Huy Nguyen
 *
 */
public class Channel {
	
	private String name;
	private String configPath;
	
	/**
	 * Channel constructor
	 * @param name
	 * @param configPath
	 */
	public Channel(String name, String configPath) {
		this.name = name;
		this.configPath = configPath;
	}
	
	/**
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return configPath
	 */
	public String getConfigPath() {
		return this.configPath;
	}

	@Override
	public String toString() {
		return "Channel [name=" + name + ", configPath=" + configPath + "]";
	}
	
}
