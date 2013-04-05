package com.paradigmcreatives.rajiworld.util.mailing;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This class contains method for sending of mails to an email ID using MailJet
 * REST API. We can use any email ID to send the mail from.
 * 
 * @author Rajeswari | Paradigm Creatives
 */
public class UserMailing {

	private String APIKey = "21b7366ffcaa0283bf7afd3f052e38e0";
	private String SecretKey = "7d34adf2043de2747ab5d49c71cc3941";

	/**
	 * Sends mail to a given ID using a from ID. It actually uses MailJET REST
	 * APIs to do so.
	 * 
	 * @param fromMailID
	 *            sender's mail ID.
	 * 
	 * @param toMailID
	 *            receiver's mail ID.
	 * 
	 * @return true if sending the mail has been successful, else false.
	 */
	public boolean send(String fromMailID, String fromUserName, String toMailID) {
		
		Properties props = new Properties();

		props.put("mail.smtp.host", "in.mailjet.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(APIKey, SecretKey);
					}
				});

		try {
			// LOG MESSAGES
			System.out.println("Sending mail - ");
			System.out.println("Sender Email ID: " + fromMailID);
			System.out.println("Sender Name: " + fromUserName);
			System.out.println("Recipient Email ID: " + toMailID);

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromMailID));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toMailID));

			message.setSubject("Email Verification");
			String sendText = "Dear, "+ fromUserName +"You are a registered user of Rajiworld"
					+ "\n  To Activate Your Account click the below link \n "
					+ "http://192.168.3.79:8080/Rajiworld/rest/emailverification/verify/"
					+ toMailID;
			/*
			 * "<html>" +
			 * "<a href= \""+"http://localhost:8080/Rajiworld/login.jsf " +
			 * "?"+toMailID +"\"" + "> Click Here </a>" + "</html>";
			 */

			message.setText(sendText);

			Transport.send(message);

			return true;
		} catch (MessagingException me) {
			System.err.println("Messaging error: " + me.getMessage());
			me.printStackTrace();
			return false;
		} catch (Exception e) {
			System.err.println("Unknown Error in sending mail: "
					+ e.getMessage());
			e.printStackTrace();
			return false;
		}
	}// end of send()

}// end of class