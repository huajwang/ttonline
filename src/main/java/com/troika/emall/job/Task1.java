package com.troika.emall.job;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class Task1 implements Tasklet {
	
	Logger logger = LogManager.getLogger(this.getClass());
	
	@StepScope
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {
		System.out.println("Task1:  Starting job..");    
		Date theDate = (Date) chunkContext.getStepContext().getJobParameters().get("d");
		System.out.println("Task1: The passed parameter from runJob1() = " + theDate);
		// ... your code        
		System.out.println("Job done..");    
		return RepeatStatus.FINISHED;}
}
