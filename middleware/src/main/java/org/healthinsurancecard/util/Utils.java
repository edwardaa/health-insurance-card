package org.healthinsurancecard.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.xml.bind.DatatypeConverter;

import org.healthinsurancecard.user.CAEnrollment;
import org.healthinsurancecard.user.UserContext;

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

}
