/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techo2.emailbatch;


import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
public class PeopleItemProcessor implements ItemProcessor<People, People> {

	private static final Logger log = LoggerFactory.getLogger(PeopleItemProcessor.class);

        @Autowired(required=true)
        EmailService emailService;
     
    @Override
    public People process(People item) throws Exception {
        
       
        emailService.emailServiceHTML(item.getEmail(), "Test Email");
         People people = new People();
        log.debug("-------mail trigger or not----");
        people.setId(item.getId());
        people.setDate(new Date());
        people.setIsSent(Boolean.TRUE);
        people.setEmail(item.getEmail());
        people.setFirstName(item.getFirstName());
        people.setLastName(item.getLastName());
        return people;
    }
   
    
}
