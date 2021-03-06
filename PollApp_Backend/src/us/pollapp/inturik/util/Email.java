package us.pollapp.inturik.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	private static final Logger log = Logger.getLogger(Email.class.getName());

	public static void sendEmail(List<String> emails, String body,
			String subject) throws Exception {

		try {

			log.info("Se van a enviar los correos. N�mero de emails: "
					+ emails.size());

			if (!emails.isEmpty()) {

				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);

				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(Constant.EMAIL_POLLAPP));

				Address[] address = new InternetAddress[emails.size()];
				int index = 0;

				for (String email : emails) {
					address[index] = new InternetAddress(email);
					log.info("Email: " + email);
					index++;
				}

				msg.setContent(body, "text/html; charset=utf-8");
				msg.addRecipients(Message.RecipientType.BCC, address);
				msg.setSubject(subject);

				Transport.send(msg);

				log.info("Correos enviados correctamente!!");

			}

		} catch (AddressException e) {
			log.info("Error = " + e.getMessage());
			throw e;
		} catch (MessagingException e) {
			log.info("Error = " + e.getMessage());
			throw e;
		} catch (Exception e) {
			log.info("Error = " + e.getMessage());
			throw e;
		}

	}

	public static void sendEmail(String email, String body,
			String subject) throws Exception {

		try {

			List<String> lstEmails = new ArrayList<>();
			lstEmails.add(email);

			sendEmail(lstEmails, body, subject);

		} catch (AddressException e) {
			log.info("Error = " + e.getMessage());
			throw e;
		} catch (MessagingException e) {
			log.info("Error = " + e.getMessage());
			throw e;
		} catch (Exception e) {
			log.info("Error = " + e.getMessage());
			throw e;
		}

	}

}
