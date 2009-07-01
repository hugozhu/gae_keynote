package com.jute.google.util;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;


/**
 * User: hugozhu
 * Date: Jul 1, 2009
 * Time: 11:06:47 PM
 */
public class Mailer {
    public static boolean send(String subject,String msgBody, String to) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("hugozhu@gmail.com"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(msgBody);
            Transport.send(msg);
            return true;
        } catch (AddressException e) {
            // ...
        } catch (MessagingException e) {
            // ...
        }
        return false;
    }
}
