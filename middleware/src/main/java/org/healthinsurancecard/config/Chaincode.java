package org.healthinsurancecard.config;

/**
 * Chaincode structure in network configuration file
 * 
 * @author Huy Nguyen
 *
 */
public class Chaincode {

	private String name;
	private String rootDir;
	private String path;
	private String version;
	
	/**
	 * Chaincode constructor
	 * 
	 * @param name
	 * @param rootDir
	 * @param path
	 * @param version
	 */
	public Chaincode(String name, String rootDir, String path, String version) {
		this.name = name;
		this.rootDir = rootDir;
		this.path = path;
		this.version = version;
	}
	
	/**
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return rootDir
	 */
	public String getRootDir() {
		return this.rootDir;
	}
	
	/**
	 * @return path
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * @return version
	 */
	public String getVersion() {
		return this.version;
	}

	@Override
	public String toString() {
		return "Chaincode [name=" + name + ", rootDir=" + rootDir + ", path=" + path + ", version=" + version + "]";
	}
	
}
