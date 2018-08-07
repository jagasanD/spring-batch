/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techo2.emailbatch;

import javax.mail.MessagingException;
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


    public void emailServiceHTML(String to, String subject) throws MessagingException {
 
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);       
            helper.setTo(to);
            helper.setText("hi ", true); // set to html
            helper.setSubject(subject);
            mailSender.send(message);
     
        
    }
    
}
