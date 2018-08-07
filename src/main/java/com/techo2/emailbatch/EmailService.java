/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techo2.emailbatch;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;

    public void emailServiceHTML(String to, String subject) throws Exception {
        Boolean isValid = crunchifyEmailValidator(to);
        if (isValid) {
            System.out.println("valide ----------");
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(to);
            helper.setText("hi ", true); // set to html
            helper.setSubject(subject);
            helper.setValidateAddresses(true);//message.setHeader("Return-Path:","<bounce@acme.com>");
            mailSender.send(message);
        } else {
           throw  new RuntimeException("Invalide Email Addreess");
        }

    }

    private boolean crunchifyEmailValidator(String email) {
        InternetAddress internetAddress;
        try {
            internetAddress = new InternetAddress(email);
            internetAddress.validate();
          
            System.out.println("You are in catch block -- Exception Occurred for: " + email + "value----");
           // return true;
            throw  new CustomError("Invalide Email Addreess");
        } catch (AddressException ex) {
            System.out.println("Errors-- Exception Occurred for: " + email + "value----");
            return false;

        } catch (Exception ex) {
            System.out.println("Errors-- Exception Occurred for: " + email + "value----");
            return false;

        }

    }

}
