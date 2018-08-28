package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.MissingResourceException;
import java.util.Properties;

public class ConfigFileReader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		ConfigFileReader configFileReader = new ConfigFileReader();
//		System.out.println(configFileReader.getProperty("chrome.driver.bin.path"));
//		System.out.println(configFileReader.getProperty("browserType"));
//		System.out.println(configFileReader.getProperty("browserType1"));
	}

	private Properties prop = null;
	private final String configPath = System.getProperty("user.dir") + "//configs//config.properties";
	private static ConfigFileReader configFileReader = null;

	private ConfigFileReader() {
		if(prop == null) {
			try(BufferedReader bfr = new BufferedReader(new FileReader(configPath))){
				prop = new Properties();
				prop.load(bfr);			
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static ConfigFileReader getInstance() {
		synchronized(ConfigFileReader.class) {
			if(configFileReader == null) {
				configFileReader = new ConfigFileReader();
			}
		}
		return configFileReader;
	}

	public String getProperty(String key) {
		String propVal = prop.getProperty(key);
		if(propVal != null)
			return propVal;
		else
			throw new MissingResourceException("Key: " + key +  " not present in config.properties", "ConfigFileReader.class", key);
	}	

}
