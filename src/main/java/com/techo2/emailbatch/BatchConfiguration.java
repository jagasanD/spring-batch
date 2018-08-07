package com.techo2.emailbatch;

import javax.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jagasan
 */
@Configuration
@Component
@EnableBatchProcessing
public class BatchConfiguration {

    private static final Logger log = LoggerFactory.getLogger(PeopleItemProcessor.class);
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Bean
    public JpaPagingItemReader<People> peopleJobReader() throws Exception {
        String jpqlQuery = "SELECT p from People p ";

        JpaPagingItemReader<People> reader = new JpaPagingItemReader<>();
        reader.setQueryString(jpqlQuery);
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(1000);
        reader.afterPropertiesSet();
        reader.setSaveState(true);

        log.debug("-------------exceute debugg--------------");
        
        return reader;
    }

    @Bean
    public PeopleItemProcessor processor() {
        return new PeopleItemProcessor();
    }

    @Bean
    public ItemWriter<People> itemWriter() {
        JpaItemWriter<People> itemWriter = new JpaItemWriter<People>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
         log.debug("-------------ItemWriter debugg--------------");
        return itemWriter;
    }

    @Bean
    public Job importUserJob() throws Exception {
         log.debug("------------- job   importUserJob debugg--------------");
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                //.listener(listener())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() throws Exception {
                 log.debug("------------- step   importUserJob debugg--------------");

        return stepBuilderFactory.get("step1")
                .<People, People>chunk(10)
                .reader(peopleJobReader())
                .processor(processor())
                .writer(itemWriter())
                .build();
    }

}
