/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tavant.mobile.womensecurity.entity;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class AlertMail {

      String server="smtp.gmail.com";
      String from="secure2305@gmail.com";
      String subject="This is an Alert Mail from your friend";
    
    public AlertMail(String receiver, String useremail) {
      
      String message    =   "Your friend "+useremail+" is in danger. Please help";
      try{
      this.sendMail(receiver,message);
      }catch(Exception e){
             e.printStackTrace();
             System.out.print("Exception"+e.getMessage());
      }
    }
   private void sendMail(String to,String messageBody) throws MessagingException, AddressException
     {

         // Setup mail server
         Properties props = System.getProperties();
         props.put("mail.smtp.host", server);
         props.put("mail.smtp.auth", "true");
         Authenticator  auth    =  new  SMTPAuthenticator();

         // Get a mail session
         Session session = Session.getDefaultInstance(props, auth);
         // Define a new mail message
         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(from));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
         message.setSubject(subject);
         // Create a message part to represent the body text
         BodyPart messageBodyPart = new MimeBodyPart();
         messageBodyPart.setText(messageBody);
         //use a MimeMultipart as we need to handle the file attachments
         Multipart multipart = new MimeMultipart();
         //add the message body to the mime message
         multipart.addBodyPart(messageBodyPart);
         // Put all message parts in the message
         message.setContent(multipart);
         // Send the message
         Transport.send(message);
     }
   private class SMTPAuthenticator extends javax.mail.Authenticator
    {

        @Override
        public PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication("secure2305@gmail.com","Welcome321");
        }
    }
}
