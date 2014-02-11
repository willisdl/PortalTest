package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropsLoad {
	public Properties getProps(){
		Properties props = new Properties();
		try {
	        //load a properties file from class path, inside static method
			//File propfile = new File("/Users/davidwillis/Documents/workspace/maritime.properties");
			File propfile = new File("portaltest.properties");
			if(propfile.exists()){
				FileInputStream fileInput = new FileInputStream(propfile); 
				props.load(fileInput);
			}else{
				System.out.println("Properties file cannot be found");
			}
	        //get the property value and print it out

		}catch (IOException ex) {
	        ex.printStackTrace();
	    }
		return props;
	}

}
