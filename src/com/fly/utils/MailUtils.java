package com.fly.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {

	// fromEmail 邮件来源谁, toEmail 邮件发给谁,emailMsg 邮件信息
	public static void sendMail(final String fromEmail, final String username, final String toEmail, String emailMsg)
			throws AddressException, MessagingException {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "SMTP");
		props.setProperty("mail.host", "localhost"); // 邮箱主机地址
		props.setProperty("mail.smtp.auth", "true");
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, "123456"); // 发送者名称
			}
		};
		// 邮件内容
		Session session = Session.getInstance(props, auth);

		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress("tom@flyindance.com")); // 发送者邮箱

		message.setRecipient(RecipientType.TO, new InternetAddress(toEmail)); // 收件人邮箱
		message.setSubject("用户激活");

		message.setContent(emailMsg, "text/html;charset=utf-8");

		Transport.send(message);
	}
}
