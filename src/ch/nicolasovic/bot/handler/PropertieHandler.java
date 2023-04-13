package ch.nicolasovic.bot.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ch.nicolasovic.bot.Console;

public class PropertieHandler {
	static Properties prop;
	
	public static String getProperty(String key){
		init(key);
		return prop.getProperty(key);
	}
	
	public static void init(String key) {
		prop = new Properties();
        String filename = "credentials.properties";
        InputStream input = PropertieHandler.class.getClassLoader().getResourceAsStream(filename);
        try {
			prop.load(input);
			Console.outputInformation("Properties loaded for property: " + key);
		} catch (IOException e) {
			Console.outputError("Unable to load properties");
		}
	}
}
