/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techo2.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author root
 */
@RestController
@CrossOrigin
public class TestController {

    @Autowired
    Job job;
    @Autowired
    JobLauncher jobLauncher;

    @GetMapping("hit")
    public void getMsg() {
        try{
            System.out.println("-----------call get method------");
        JobParameters jobParameters
                = new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis()).toJobParameters();

        jobLauncher.run(job, jobParameters);
        }catch(Exception e){
            e.printStackTrace();
        }        

    }

}
