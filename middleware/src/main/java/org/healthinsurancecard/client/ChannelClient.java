package org.healthinsurancecard.client;

import org.hyperledger.fabric.sdk.Channel;

/**
 * Channel Client
 * @author Huy Nguyen
 *
 */
public class ChannelClient {

	private String name;
	private Channel channel;
	private FabricClient fabricClient;
	
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return channel
	 */
	public Channel getChannel() {
		return channel;
	}
	/**
	 * @return fabricClient
	 */
	public FabricClient getFabricClient() {
		return fabricClient;
	}
	
	public ChannelClient(String name, Channel channel, FabricClient fabricClient) {
		this.name = name;
		this.channel = channel;
		this.fabricClient = fabricClient;
		
	}
}
