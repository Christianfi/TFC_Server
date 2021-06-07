/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.entities.Comic;

/**
 *
 * @author CHRISTIAN
 */
public class EmailService {

    private String myAccount;
    private String password;

    public EmailService() {
        loadProperties();
    }

    private void loadProperties() {
        Properties emailProp = new Properties();
        InputStream is = this.getClass().getResourceAsStream("/properties/email_account.properties");
        try {
            emailProp.load(is);
        } catch (IOException ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
        }

        myAccount = emailProp.getProperty("account");
        password = emailProp.getProperty("password");
    }

    /**
     * Creates a session using the email account
     *
     * @return The Session created
     */
    private Session login() {

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccount, password);
            }
        });

        return session;
    }

    /**
     * Creates a new message to send
     *
     * @param session The session to send the email
     * @param recipient Recipient of the email
     * @param subject Subject of the email
     * @param text Message content
     * @return The Message object to send
     */
    private Message prepareMessage(Session session, InternetAddress[] recipients, String subject, String text) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccount));
            message.setRecipients(Message.RecipientType.BCC, recipients);
            message.setSubject(subject);
            message.setText(text);

            return message;
        } catch (MessagingException e) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public int sendNewComicNotification(String[] recipients, Comic c) {
        String text = "¡Hola!\nTe informamos de que el comic " + c.getName() + " numero #" + c.getNumber()
                + " de la coleccion " + c.getCollection().getName() + " a la que estas suscrito,"
                + "ya está disponible en nuestra tienda";
        Message message = prepareMessage(login(), stringToAddress(recipients), "Nuevo comic disponible", text);
        try {
            Transport.send(message);
            return 0;
        } catch (MessagingException ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public InternetAddress[] stringToAddress(String[] addresses) {
        InternetAddress[] result = new InternetAddress[addresses.length];

        for (int i = 0; i < result.length; i++) {
            try {
                result[i] = new InternetAddress(addresses[i]);
            } catch (AddressException ex) {
                Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return result;
    }
}
