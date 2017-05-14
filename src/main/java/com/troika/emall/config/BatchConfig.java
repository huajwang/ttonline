package com.troika.emall.config;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.troika.emall.job.Task1;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfig {
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	private JobBuilderFactory jobs;
	@Autowired
	private StepBuilderFactory steps;
	
	@Bean
	public Step step1() {    
		return steps.get("step1").tasklet(new Task1()).build();
	}  
	
	@Bean
	public Job job1() {    
		return jobs.get("job1").start(step1())
				//.next(step2())
				.build();
	}
	// test purpose only
	@Scheduled(cron="0 0 2 * * ?")
	public void runJob1() throws Exception {
		System.out.println("在BatchConfig 里的 runJob1: run scheduler every 60 seconds...");
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addDate("d", new Date());      
		jobLauncher.run(job1(), jobParametersBuilder.toJobParameters());
	}
}
