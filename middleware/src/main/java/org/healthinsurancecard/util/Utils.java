package org.healthinsurancecard.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

import org.healthinsurancecard.client.ChannelClient;
import org.healthinsurancecard.client.FabricClient;
import org.healthinsurancecard.config.NetworkConfig;
import org.healthinsurancecard.user.CAEnrollment;
import org.healthinsurancecard.user.UserContext;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.ChannelConfiguration;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class Utils {
	
	/**
	 * Write user context
	 * 
	 * @param usercontext user context
	 * @throws Exception
	 */
	public static void writeUserContext(UserContext usercontext) throws Exception {
		String dirPath = "users/" + usercontext.getAffiliation();
		String filePath = dirPath + "/" + usercontext.getName() + ".ser";
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		FileOutputStream file = new FileOutputStream(filePath);
		ObjectOutputStream out = new ObjectOutputStream(file);
		out.writeObject(usercontext);
		out.close();
		file.close();
	}
	
	/**
	 * Read user context
	 * @param affliliation
	 * @param username user name
	 * @return userContext object
	 * @throws Exception
	 */
	public static UserContext readUserContext(String affiliation, String username) throws Exception {
		String filePath = "users/" + affiliation + "/" + username + ".ser";
		File file = new File(filePath);
		if (file.exists()) {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(fileInputStream);
			
			UserContext userContext = (UserContext) in.readObject();
			in.close();
			fileInputStream.close();
			return userContext;
		}
		return null;
	}
	
	public static CAEnrollment getEnrollment(String keyFolderPath,
			String keyFileName,
			String certFolderPath,
			String certFileName) throws Exception {
		PrivateKey key = null;
		String certificate = null;
		InputStream keyInputStream = null;
		BufferedReader brKey = null;
		
		try {
			keyInputStream = new FileInputStream(keyFolderPath + File.separator + keyFileName);
			brKey = new BufferedReader(new InputStreamReader(keyInputStream));
			StringBuilder keyBuilder = new StringBuilder();
			for (String line = brKey.readLine(); line != null; line = brKey.readLine()) {
				if (line.indexOf("PRIVATE") != -1) {
					keyBuilder.append(line);
				}
			}
			
			certificate = new String(Files.readAllBytes(Paths.get(certFolderPath, certFileName)));
			byte[] encoded = DatatypeConverter.parseBase64Binary(keyBuilder.toString());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
			KeyFactory kf = KeyFactory.getInstance("ECDSA");
			key = kf.generatePrivate(keySpec);
			
		} finally {
			keyInputStream.close();
			brKey.close();
		}
		CAEnrollment enrollment = new CAEnrollment(key, certificate);
		return enrollment;
	}
	
	public static void cleanUp() {
		String dirPath = "users";
		File dir = new File(dirPath);
		deleteDir(dir);
		
	}
	
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) {
				boolean isSuccess = deleteDir(files[i]);
				if (!isSuccess) {
					return false;
				}
			}
		}
		return dir.delete();
	}
	
	public static UserContext getAdminUserContext(String keyPath, String certPath, String mspName, String adminName) throws Exception {
		UserContext adminUserContext = new UserContext();
		File pkFolder1 = new File(keyPath);
		File[] pkFiles1 = pkFolder1.listFiles();
		
		File certFolder1 = new File(certPath);
		File[] certFiles1 = certFolder1.listFiles();
		Enrollment enrollOrg1Admin = getEnrollment(keyPath, pkFiles1[0].getName(),
				certPath, certFiles1[0].getName());
		adminUserContext.setEnrollment(enrollOrg1Admin);
		adminUserContext.setMspId(mspName);
		adminUserContext.setName(adminName);
		return adminUserContext;
	}
	
	/**
	 * Create and join peers to the channel
	 * @param adminUserContext
	 * @param channelName
	 * @param orderName
	 * @param ordererURL
	 * @param peers
	 * @return
	 * @throws Exception
	 */
	public static Channel createAndJoinPeersToChannel(UserContext adminUserContext, String channelName, 
			String orderName, String ordererURL, List<Peer> peers) throws Exception {
		FabricClient fabClient = new FabricClient(adminUserContext);

		// Create a new channel
		Orderer orderer = fabClient.getInstance().newOrderer(orderName, ordererURL);
		ChannelConfiguration channelConfiguration = new ChannelConfiguration(new File(channelName));

		byte[] channelConfigurationSignatures = fabClient.getInstance()
				.getChannelConfigurationSignature(channelConfiguration, adminUserContext);

		Channel mychannel = fabClient.getInstance().
				newChannel(channelName, orderer, channelConfiguration,
				channelConfigurationSignatures);
		
		for (Peer peer : peers) {
			mychannel.joinPeer(peer);
		}
		
		mychannel.addOrderer(orderer);

		mychannel.initialize();
		
		return mychannel;
	}
	
	/**
	 * Install the chaincode on an organization
	 * 
	 * @param adminUserContext
	 * @param chaincodeName
	 * @param chaincodeRootDir
	 * @param chaincodePath
	 * @param chaincodeVersion
	 * @param chaincodeLanguage
	 * @param peers
	 * @return
	 * @throws Exception
	 */
	public static Collection<ProposalResponse> installChaincodeOnOrganization(UserContext adminUserContext, String chaincodeName,
			String chaincodeRootDir, String chaincodePath, String chaincodeVersion, String chaincodeLanguage, Collection<Peer> peers) throws Exception {
		FabricClient fabClient = new FabricClient(adminUserContext);
		Collection<ProposalResponse> responses = fabClient.installChaincode(chaincodeName,
				chaincodePath, chaincodeRootDir, chaincodeLanguage, chaincodeVersion, peers);
		return responses;
	}
	
	/**
	 * Instantiate chaincode on channel
	 * 
	 * @param channelClient
	 * @param chaincodeName
	 * @param chaincodeVerion
	 * @param chaincodePath
	 * @param chaincodeLanguage
	 * @param args
	 * @param policyPath
	 * @return
	 * @throws Exception
	 */
	public static Collection<ProposalResponse> instantiateChaincodeOnChannel(
			ChannelClient channelClient, String chaincodeName, String chaincodeVerion,
			String chaincodePath, String chaincodeLanguage, String[] args, String policyPath) throws Exception {
		
		Collection<ProposalResponse> responses = channelClient.instantiateChaincode(chaincodeName, chaincodeVerion,
				chaincodePath, chaincodeLanguage, "init", args, policyPath);
		return responses;
	}
	
	public static NetworkConfig getNetworkConfig(String filePath) throws Exception {
		NetworkConfig config = new NetworkConfig(filePath);
		return config;
	}
	
}
