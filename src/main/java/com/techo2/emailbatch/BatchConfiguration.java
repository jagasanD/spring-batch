
package com.techo2.emailbatch;

import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Jagasan
 */

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
      @Autowired
     private  EntityManagerFactory entityManagerFactory;
    
    
    
    @Bean
    public JpaPagingItemReader<People> peopleJobReader() throws Exception {
        String jpqlQuery = "SELECT p from People p ";

        JpaPagingItemReader<People> reader = new JpaPagingItemReader<>();
        reader.setQueryString(jpqlQuery);
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(1000);
        reader.afterPropertiesSet();
        reader.setSaveState(true);

        return reader;
    }

    @Bean
    public PeopleItemProcessor processor() {
        return new PeopleItemProcessor();
    }
    @Bean
   public ItemWriter writer(){
       
    
    return null;
    }
    
    @Bean
    public Job importUserJob() throws Exception {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                //.listener(listener())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .<People, MimeMessage> chunk(10)
                .reader(peopleJobReader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    
    
    
    
}
