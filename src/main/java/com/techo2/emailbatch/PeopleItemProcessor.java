/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techo2.emailbatch;


import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 *
 * @author root
 */
public class PeopleItemProcessor implements ItemProcessor<People, MimeMessage> {

	private static final Logger log = LoggerFactory.getLogger(PeopleItemProcessor.class);

	@Autowired
	private JavaMailSender mailSender;

    @Override
    public MimeMessage process(People item) throws Exception {
        
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        /*Map<String, Object> model = new HashMap<>();
		model.put("name", student.getFullname());
		model.put("code", student.getCode());*/
        helper.setFrom("jagasan.dansena@techp2.com");
        helper.setTo(item.getEmail());
        //helper.setCc(sender);
        helper.setSubject("test Batch API");
        helper.setText("hi");

        log.info("Preparing message for: " + item.getEmail());
        
      //  System.out.println("-------------hello-----------------------");

        //FileSystemResource file = new FileSystemResource(attachment);
        //helper.addAttachment(file.getFilename(), file);

		return message;
        
        
    }
    
}
