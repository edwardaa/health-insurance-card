package org.healthinsurancecard.client;

import java.io.File;
import java.util.Collection;

import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.InstallProposalRequest;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

/**
 * Fabric Client wrapper class
 * 
 * @author Huy Nguyen
 *
 */
public class FabricClient {

	private HFClient instance;
	
	/**
	 * @return instance of HFClient
	 */
	public HFClient getInstance() {
		return instance;
	}
	
	/**
	 * Fabric Client constructor
	 * @param userContext user context
	 * @throws Exception
	 */
	public FabricClient(User userContext) throws Exception {
		CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
		instance = HFClient.createNewInstance();
		instance.setCryptoSuite(cryptoSuite);
		instance.setUserContext(userContext);
	}
	
	/**
	 * Create Channel
	 * @param name
	 * @return channel client
	 * @throws Exception
	 */
	public ChannelClient createChannel(String name) throws Exception {
		Channel channel = instance.newChannel(name);
		ChannelClient channelClient = new ChannelClient(name, channel, this);
		return channelClient;
	}
	
	/**
	 * Install the chaincode
	 * @param chaincodeName chaincode name
	 * @param chaincodePath chaincode path
	 * @param codePath code path
	 * @param language
	 * @param version
	 * @param peers
	 * @return collection of proposal response
	 * @throws Exception
	 */
	public Collection<ProposalResponse> installChaincode(
			String chaincodeName,
			String chaincodePath,
			String codePath,
			String language,
			String version,
			Collection<Peer> peers) throws Exception {
		InstallProposalRequest request = instance.newInstallProposalRequest();
		ChaincodeID.Builder chaincodeIDBuilder = ChaincodeID.newBuilder().setName(chaincodeName)
				.setVersion(version).setPath(chaincodePath);
		ChaincodeID chaincodeID = chaincodeIDBuilder.build();
		request.setChaincodeID(chaincodeID);
		request.setUserContext(instance.getUserContext());
		request.setChaincodeSourceLocation(new File(codePath));
		request.setChaincodeVersion(version);
		Collection<ProposalResponse> responses = instance.sendInstallProposal(request, peers);
		return responses;
	}
}
