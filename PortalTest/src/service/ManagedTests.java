package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.*;



public class ManagedTests extends SeleneseTestCase {

	//private String baseUrl = "http://msi.nga.mil/";
	private String failMessage = "";
	private static Logger msilogger = Logger.getLogger(ManagedTests.class);
	
	public boolean testManaged(Properties props){
		boolean msiPass = true;
		boolean portalPass = true;
		boolean portal3Pass = true;
		boolean DNCLoaderPass = true;
		boolean MSILoaderPass = true;
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		String msgPart1 = "The weblogic portal server for the Maritime site (msi.nga.mil) running on";
		String msgPart2 = "is not responding.  Please contact the Webops on-call person.\nThe Maritime site remains fully functional as long as at least 1 of the 3 servers is functioning.\n\nPlease note that the site is subject to an Authorized Outage (17045) each Tuesday between 2200Z and 0200Z.";
		String sendto = props.getProperty("sendto");
		EmailUs sendMail = new EmailUs();
		String[] addresses = sendto.split(",");
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		//WebDriver driver = new FirefoxDriver();
		//selenium = new WebDriverBackedSelenium(driver, baseUrl);
		//String portal = null;
//		if (address == "164.214.13.143:7001") portal = "Portal1";
//		if (address == "164.214.13.144:7001") portal = "Portal2";
//		if (address == "164.214.13.145:7001") portal = "Portal3";
//		if (address == "164.214.13.153:7101") portal = "Managed1";
//		if (address == "164.214.13.153:7102") portal = "Managed2";
//		if (address == "164.214.13.141") portal = "Apache1";
//		if (address == "164.214.13.142") portal = "Apache2";
		try{
			setUp("http://" + props.getProperty("managed1") + "/", "*firefox");
		}catch (Exception e){
			System.out.println("setUp failed!");
			e.printStackTrace();
		}
		
		try{
			selenium.setTimeout("120000");
			selenium.open("/NGAMSILoader");
			//selenium.waitForPageToLoad("60000");
			if (selenium.isTextPresent("NGA MSI Loader") != true){
				msiPass = false;
				msilogger.error("ManagedTest failed - MSI Loader failed to load.");
				failMessage = failMessage + "MSI Loader failed to load.\n \n";
			}
			selenium.stop();
		}catch(Exception e){
			msilogger.error("MSI Loader failed to load.");
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			msilogger.error(exceptionAsString);
			MSILoaderPass = false;
			msiPass = false;
			failMessage = failMessage + "MSI Loader failed to load.\n \n" + exceptionAsString + "\n";
		}
		
		selenium.stop();
		
		try{
			setUp("http://" + props.getProperty("managed1") + "/", "*firefox");
		}catch (Exception e){
			System.out.println("setUp failed!");
			e.printStackTrace();
		}
		
		try{
			selenium.setTimeout("90000");
			selenium.open("/DNCUploader");
			//selenium.waitForPageToLoad("60000");
			if (selenium.isTextPresent("DNC Upload Console") != true){
				DNCLoaderPass = false;
				msiPass = false;
				msilogger.error("ManagedTest failed - DNC Loader failed to load.");
				failMessage = failMessage + "DNC Loader failed to load.\n \n";
			}
		}catch(Exception e){
			msilogger.error("DNC Loader failed to load.");
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			msilogger.error(exceptionAsString);
			msiPass = false;
			failMessage = failMessage + "DNC Loader failed to load.\n \n" + exceptionAsString + "\n";
		}
		
		selenium.stop();
		
		try{
			setUp("http://" + props.getProperty("managed1") + "/", "*firefox");
		}catch (Exception e){
			System.out.println("setUp failed!");
			e.printStackTrace();
		}
		
		try{
			selenium.setTimeout("90000");
			selenium.open("/NGAPortalAppAdmin");
			//selenium.waitForPageToLoad("60000");
			if (selenium.isTextPresent("Sign in to work with WebLogic Portal") != true){
				msiPass = false;
				portalPass = false;
				msilogger.error("ManagedTest failed - PortalAppAdmin failed to load.");
				failMessage = failMessage + "PortalAppAdmin failed to load.\n \n";
			}
		}catch(Exception e){
			msilogger.error("PortalAppAdmin failed to load.");
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			msilogger.error(exceptionAsString);
			msiPass = false;
			failMessage = failMessage + "PortalAppAdmin failed to load.\n \n" + exceptionAsString + "\n";
		}
		
		selenium.stop();
		
		if(props.getProperty("managed3") != null){
			try{
				setUp("http://" + props.getProperty("managed3") + "/", "*firefox");
			}catch (Exception e){
				System.out.println("setUp failed!");
				e.printStackTrace();
			}
			
			try{
				selenium.setTimeout("90000");
				selenium.open("/NGAPortalAppAdmin");
				//selenium.waitForPageToLoad("60000");
				if (selenium.isTextPresent("Sign in to work with WebLogic Portal") != true){
					msiPass = false;
					portal3Pass = false;
					msilogger.error("ManagedTest failed - PortalAppAdmin failed to load.");
					failMessage = failMessage + "PortalAppAdmin failed to load.\n \n";
				}
			}catch(Exception e){
				msilogger.error("PortalAppAdmin failed to load.");
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				msilogger.error(exceptionAsString);
				msiPass = false;
				failMessage = failMessage + "PortalAppAdmin failed to load.\n \n" + exceptionAsString + "\n";
			}
			selenium.stop();
		}
		
		selenium.stop();
		if(msiPass == true)msilogger.info("ManagedTest passed.");
		
		if((portalPass == false)){ //|| (dncPass == false) || (jauPass == false)){
			/*
			try {
				Runtime.getRuntime().exec("C:\\Perl\\bin\\perl portaladmintest.pl");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			sendMail.notifyMail(addresses[0], "The NGAPortalAppAdmin console on Managed1" + msgPart2);
			msilogger.info("Email sent to " + addresses[0]);
			sendMail.notifyMail(addresses[1], "The NGAPortalAppAdmin console on Managed1" + msgPart2);
			msilogger.info("Email sent to " + addresses[1]);
			if(addresses[2] != null){
				if(hour >= 17 || hour <= 8){
					sendMail.notifyMail(addresses[2], "The NGAPortalAppAdmin console on Managed1" + msgPart2);
					msilogger.info("Email sent to " + addresses[2]);
				}
			}
		}
		
		/*
		if((portal3Pass == false)){ //|| (dncPass == false) || (jauPass == false)){
			
			try {
				Runtime.getRuntime().exec("C:\\Perl\\bin\\perl portal3admintest.pl");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendMail.notifyMail(addresses[0], "The NGAPortalAppAdmin console " + msgPart2);
			msilogger.info("Email sent to " + addresses[0]);
			sendMail.notifyMail(addresses[1], "The NGAPortalAppAdmin console " + msgPart2);
			msilogger.info("Email sent to " + addresses[1]);
			if(hour >= 17 || hour <= 8){
				sendMail.notifyMail(addresses[2], "The NGAPortalAppAdmin console " + msgPart2);
				msilogger.info("Email sent to " + addresses[2]);
		}
		*/
		
		if((DNCLoaderPass == false)){ //|| (dncPass == false) || (jauPass == false)){
			/*
			try {
				Runtime.getRuntime().exec("C:\\Perl\\bin\\perl dncloadertest.pl");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			sendMail.notifyMail(addresses[0], "The DNCUploader on Managed1" + msgPart2);
			msilogger.info("Email sent to " + addresses[0]);
			sendMail.notifyMail(addresses[1], "The DNCUploader on Managed1" + msgPart2);
			msilogger.info("Email sent to " + addresses[1]);
			if(addresses[2] != null){
				if(hour >= 17 || hour <= 8){
					sendMail.notifyMail(addresses[2], "The DNCUploader on Managed1" + msgPart2);
					msilogger.info("Email sent to " + addresses[2]);
				}
			}
		}
		
		if((MSILoaderPass == false)){ //|| (dncPass == false) || (jauPass == false)){
			/*
			try {
				Runtime.getRuntime().exec("C:\\Perl\\bin\\perl msiloadertest.pl");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			sendMail.notifyMail(addresses[0], "The NGAMSILoader on Managed1" + msgPart2);
			msilogger.info("Email sent to " + addresses[0]);
			sendMail.notifyMail(addresses[1], "The NGAMSILoader on Managed1" + msgPart2);
			msilogger.info("Email sent to " + addresses[1]);
			if(addresses[2] != null){
				if(hour >= 17 || hour <= 8){
					sendMail.notifyMail(addresses[2], "The NGAMSILoader on Managed1" + msgPart2);
					msilogger.info("Email sent to " + addresses[2]);
				}
			}
		}
		
		return msiPass;
	}
	
	public String getFailure(){
		return failMessage;
	}

}
