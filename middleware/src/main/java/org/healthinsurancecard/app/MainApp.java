package org.healthinsurancecard.app;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.healthinsurancecard.config.NetworkConfig;
import org.healthinsurancecard.util.Utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class MainApp {
	public static void main(String[] args) {
		try {
			System.out.println(Utils.getNetworkConfig("config/config.json").toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getFileContent(String filePath) {
		StringBuilder result = new StringBuilder("");

		//Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(filePath).getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return result.toString();
	}

}
