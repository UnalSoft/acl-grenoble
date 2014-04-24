package com.unsoft.acl_grenoble.util;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Edward
 */
public class GestionMail {

    final String EMAIL = "centre.loisirs.grenoble@gmail.com";
    final String MOTDEPASS = "webprojet";
    final String SERVEURSMTP = "smtp.gmail.com";
    final String PORT = "465";

    public void envoyerMail(String mailDestinataire, String sujet, String text, String[] files) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.user", EMAIL);
        props.put("mail.smtp.host", SERVEURSMTP);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        SecurityManager security = System.getSecurityManager();
        Multipart mp = new MimeMultipart();
        
        Authenticator auth = new autentificadorSMTP();
        Session session = Session.getInstance(props, auth);
        // session.setDebug(true);

        MimeMessage msg = new MimeMessage(session);
        msg.setText(text);
        msg.setSubject(sujet);

        if (files != null) {
            
            for (String file : files) {
                MimeBodyPart attach = new MimeBodyPart();
                attach.setDataHandler(new DataHandler(new FileDataSource(file)));
                attach.setFileName(new FileDataSource(file).getName());
                mp.addBodyPart(attach);
            }
            
            msg.setContent(mp);
        }

        msg.setFrom(new InternetAddress(EMAIL));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailDestinataire));
        Transport.send(msg);

    }

    private class autentificadorSMTP extends javax.mail.Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(EMAIL, MOTDEPASS);
        }
    }

}
