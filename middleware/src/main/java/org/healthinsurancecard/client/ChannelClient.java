package org.healthinsurancecard.client;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hyperledger.fabric.sdk.ChaincodeEndorsementPolicy;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.InstantiateProposalRequest;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.BlockEvent.TransactionEvent;
import org.hyperledger.fabric.sdk.TransactionRequest.Type;

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
	
	public Collection<ProposalResponse> instantiateChaincode(
			String chaincodeName,
			String version,
			String chaincodePath,
			String language,
			String functionName,
			String [] functionArgs,
			String policyPath) throws Exception {
		
		InstantiateProposalRequest request = fabricClient.getInstance().newInstantiationProposalRequest();
		request.setProposalWaitTime(180000);
		ChaincodeID.Builder ccIdBuilder = ChaincodeID.newBuilder().setName(chaincodeName)
				.setVersion(version).setPath(chaincodePath);
		ChaincodeID ccID = ccIdBuilder.build();
		request.setChaincodeID(ccID);
		request.setChaincodeLanguage(Type.GO_LANG);
		request.setFcn(functionName);
		request.setArgs(functionArgs);
		Map<String, byte[]> tm = new HashMap<>();
		tm.put("HyperLedgerFabric", "InstantiateProposalRequest:JavaSDK".getBytes(UTF_8));
		tm.put("method", "InstantiateProposalRequest".getBytes(UTF_8));
		request.setTransientMap(tm);
		
		if (policyPath != null) {
			ChaincodeEndorsementPolicy policy = new ChaincodeEndorsementPolicy();
			policy.fromFile(new File(policyPath));
			request.setChaincodeEndorsementPolicy(policy);
		}
		
		Collection<ProposalResponse> responses = channel.sendInstantiationProposal(request);
		CompletableFuture<TransactionEvent> cf = channel.sendTransaction(responses);
		Logger.getLogger(ChannelClient.class.getName()).log(Level.INFO,
				"Chaincode " + chaincodeName + " on channel " + channel.getName() + " instantiation " + cf);
		
		return responses;
		
	}
}
