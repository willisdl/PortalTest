package business;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.*;

import service.*;

public class testMain {
	private static Logger testlogger = Logger.getLogger(testMain.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean msiPass1 = true;
		boolean msiPass2 = true;
		boolean msiPass3 = true;
		boolean msiPass4 = true;
		boolean msiPass5 = true;
		boolean apachePass1 = true;
		boolean apachePass2 = true;
		boolean managedPass = true;
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		PropsLoad loadProps = new PropsLoad();
		Properties props = loadProps.getProps();
		String msgPart1 = "The weblogic portal server for the Maritime site (msi.nga.mil) running on";
		String msgPart2 = "is not responding.  Please contact the Webops on-call person.\nThe Maritime site remains fully functional as long as at least 1 of the 3 servers is functioning.\n\nPlease note that the site is subject to an Authorized Outage (17045) each Tuesday between 2200Z and 0200Z.";
		String sendto = props.getProperty("sendto");
		String[] addresses = sendto.split(",");
		MSITest msi = new MSITest();
		ManagedTests manage = new ManagedTests();
		EmailUs sendMail = new EmailUs();
		int i;
		
		msiPass1 = msi.testMSI(props, "portal1");
		msiPass2 = msi.testMSI(props, "portal2");
		msiPass3 = msi.testMSI(props, "portal3");
		msiPass4 = msi.testMSI(props, "managed1");
		if(props.getProperty("managed2") != null){
			msiPass5 = msi.testMSI(props, "managed2");
		}
		apachePass1 = msi.testMSI(props, "apache1");
		apachePass2 = msi.testMSI(props, "apache2");
		managedPass = manage.testManaged(props);
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		
		if((msiPass1 == false)){
			/*
			try {
				Runtime.getRuntime().exec("C:\\Perl\\bin\\perl portal1test.pl");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			sendMail.notifyMail(addresses[0], msgPart1 + " Portal1 " + msgPart2);
			testlogger.info("Email sent to " + addresses[0]);
			sendMail.notifyMail(addresses[1], msgPart1 + " Portal1 " + msgPart2);
			testlogger.info("Email sent to " + addresses[1]);
			for (i = 2; i < addresses.length; i++){
				if(addresses[i] != null){
					if(hour >= 17 || hour <= 8){
						sendMail.notifyMail(addresses[i], msgPart1 + " Portal1 " + msgPart2);
						testlogger.info("Email sent to " + addresses[i]);
					}
				}
			}
			
			
		}
		
		if((msiPass2 == false)){ //|| (dncPass == false) || (jauPass == false)){
			/*
			try {
				Runtime.getRuntime().exec("C:\\Perl\\bin\\perl portal2test.pl");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			sendMail.notifyMail(addresses[0], msgPart1 + " Portal2 " + msgPart2);
			testlogger.info("Email sent to " + addresses[0]);
			sendMail.notifyMail(addresses[1], msgPart1 + " Portal2 " + msgPart2);
			testlogger.info("Email sent to " + addresses[1]);
			for (i = 2; i < addresses.length; i++){
				if(addresses[i] != null){
					if(hour >= 17 || hour <= 8){
						sendMail.notifyMail(addresses[i], msgPart1 + " Portal2 " + msgPart2);
						testlogger.info("Email sent to " + addresses[i]);
					}
				}
			}
			
		}
		if((msiPass3 == false)){ //|| (dncPass == false) || (jauPass == false)){
			/*
			try {
				Runtime.getRuntime().exec("C:\\Perl\\bin\\perl portal3test.pl");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			sendMail.notifyMail(addresses[0], msgPart1 + " Portal3 " + msgPart2);
			testlogger.info("Email sent to " + addresses[0]);
			sendMail.notifyMail(addresses[1], msgPart1 + " Portal3 " + msgPart2);
			testlogger.info("Email sent to " + addresses[1]);
			for (i = 2; i < addresses.length; i++){
				if(addresses[i] != null){
					if(hour >= 17 || hour <= 8){
						sendMail.notifyMail(addresses[i], msgPart1 + " Portal3 " + msgPart2);
						testlogger.info("Email sent to " + addresses[i]);
					}
				}
			}
			
		}
		if((msiPass4 == false)){ //|| (dncPass == false) || (jauPass == false)){
			/*
			try {
				Runtime.getRuntime().exec("C:\\Perl\\bin\\perl managed1test.pl");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			sendMail.notifyMail(addresses[0], msgPart1 + " Managed1 " + msgPart2);
			testlogger.info("Email sent to " + addresses[0]);
			sendMail.notifyMail(addresses[1], msgPart1 + " Managed1 " + msgPart2);
			testlogger.info("Email sent to " + addresses[1]);
			for (i = 2; i < addresses.length; i++){
				if(addresses[i] != null){
					if(hour >= 17 || hour <= 8){
						sendMail.notifyMail(addresses[i], msgPart1 + " Managed1 " + msgPart2);
						testlogger.info("Email sent to " + addresses[i]);
					}
				}
			}
			
		}
		if((msiPass5 == false)){ //|| (dncPass == false) || (jauPass == false)){
			/*
			try {
				Runtime.getRuntime().exec("C:\\Perl\\bin\\perl managed2test.pl");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			sendMail.notifyMail(addresses[0], msgPart1 + " Managed2 " + msgPart2);
			testlogger.info("Email sent to " + addresses[0]);
			sendMail.notifyMail(addresses[1], msgPart1 + " Managed2 " + msgPart2);
			testlogger.info("Email sent to " + addresses[1]);
			for (i = 2; i < addresses.length; i++){
				if(addresses[i] != null){
					if(hour >= 17 || hour <= 8){
						sendMail.notifyMail(addresses[i], msgPart1 + " Managed2 " + msgPart2);
						testlogger.info("Email sent to " + addresses[i]);
					}
				}
			}
			
		}
		
		if((apachePass1 == false)){ //|| (dncPass == false) || (jauPass == false)){
			/*
			try {
				Runtime.getRuntime().exec("C:\\Perl\\bin\\perl apache1test.pl");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			sendMail.notifyMail(addresses[0], msgPart1 + " Apache1 " + msgPart2);
			testlogger.info("Email sent to " + addresses[0]);
			sendMail.notifyMail(addresses[1], msgPart1 + " Apache1 " + msgPart2);
			testlogger.info("Email sent to " + addresses[1]);
			for (i = 2; i < addresses.length; i++){
				if(addresses[i] != null){
					if(hour >= 17 || hour <= 8){
						sendMail.notifyMail(addresses[i], msgPart1 + " Apache1 " + msgPart2);
						testlogger.info("Email sent to " + addresses[i]);
					}
				}
			}
			
		}
		
		if((apachePass2 == false)){ //|| (dncPass == false) || (jauPass == false)){
			/*
			try {
				Runtime.getRuntime().exec("C:\\Perl\\bin\\perl apache2test.pl");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			sendMail.notifyMail(addresses[0], msgPart1 + " Apache2 " + msgPart2);
			testlogger.info("Email sent to " + addresses[0]);
			sendMail.notifyMail(addresses[1], msgPart1 + " Apache2 " + msgPart2);
			testlogger.info("Email sent to " + addresses[1]);
			for (i = 2; i < addresses.length; i++){
				if(addresses[i] != null){
					if(hour >= 17 || hour <= 8){
						sendMail.notifyMail(addresses[i], msgPart1 + " Apache2 " + msgPart2);
						testlogger.info("Email sent to " + addresses[i]);
					}
				}
			}
			
		}
	}
	
	
}
