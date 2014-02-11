package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.*;



public class MSITest extends SeleneseTestCase {

	//private String baseUrl = "http://msi.nga.mil/";
	private String failMessage = "";
	private static Logger msilogger = Logger.getLogger(MSITest.class);
	
	public boolean testMSI(Properties props, String portal){
		boolean msiPass = true;
		//WebDriver driver = new FirefoxDriver();
		//selenium = new WebDriverBackedSelenium(driver, baseUrl);
		//String portal = null;
		String address = props.getProperty(portal);
		/*
		if (address == "164.214.13.143:7001") portal = "Portal1";
		if (address == "164.214.13.144:7001") portal = "Portal2";
		if (address == "164.214.13.145:7001") portal = "Portal3";
		if (address == "164.214.13.153:7101") portal = "Managed1";
		if (address == "164.214.13.153:7102") portal = "Managed2";
		if (address == "164.214.13.141") portal = "Apache1";
		if (address == "164.214.13.142") portal = "Apache2";
		*/
		try{
			setUp("http://" + address + "/", "*firefox");
		}catch (Exception e){
			System.out.println("setUp failed!");
			e.printStackTrace();
		}
		
		try{
			selenium.setTimeout("90000");
			selenium.open("/NGAPortal/MSI.portal");
			//selenium.waitForPageToLoad("60000");
			if (selenium.isTextPresent("Maritime Safety Information") != true){
				msiPass = false;
				msilogger.error("MSITest failed - MSI Homepage failed to load on " + portal + ".");
				failMessage = failMessage + "MSI Homepage failed to load.\n \n http://msi.nga.mil/NGAPortal/MSI.portal?_nfpb=true&_pageLabel=msi_home_page \n";
			}
		}catch(Exception e){
			msilogger.error("MSI Portal homepage failed to load on " + portal +".");
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			msilogger.error(exceptionAsString);
			msiPass = false;
			/*
			try{
				Runtime.getRuntime().exec("C:\\Perl\\bin\\perl " + portal + "test.pl");
			} catch(IOException e1){
				e1.printStackTrace();
			}
			*/
			failMessage = failMessage + "MSI Homepage failed to load.\n \n http://msi.nga.mil/NGAPortal/MSI.portal?_nfpb=true&_pageLabel=msi_home_page \n" + exceptionAsString + "\n";
		}
		
		selenium.stop();
		if(msiPass == true)msilogger.info("MSITest passed on " + portal + ".");
		/*
		if(msiPass == false) {
			try{
				Runtime.getRuntime().exec("C:\\Perl\\bin\\perl " + portal + "test.pl");
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		*/
		return msiPass;
	}
	
	public String getFailure(){
		return failMessage;
	}

}
