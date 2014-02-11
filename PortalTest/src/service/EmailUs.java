package service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUs {

	public void notifyMail (String receiver, String msgText){
		Properties props = new Properties();
		props.put("mail.smtp.host", "214.28.226.110");
		props.put("mail.debug", "true");
		
		Session session = Session.getInstance(props);
		try{
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("webops_nce@nga.ic.gov"));
			InternetAddress[] address = {new InternetAddress(receiver)};
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject("** MARITIME ISSUE **");
			msg.setSentDate(new Date());
			msg.setText(msgText);
			Transport.send(msg);
		}catch(MessagingException e){
			e.printStackTrace();
		}
	}
	
}
