package br.com.sicredi.sincronizacontas.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/load")
public class LoadController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @GetMapping
    public BatchStatus load() {
        final Map<String, JobParameter> mapParams = new HashMap<>();
        mapParams.put("time", new JobParameter(System.currentTimeMillis()));
        final JobParameters jobParameters = new JobParameters(mapParams);
        JobExecution jobExecution = null;
        try {
            jobExecution = jobLauncher.run(job, jobParameters);
            System.out.println("Jobexecution ID " + jobExecution.getJobId() + " status " + jobExecution.getStatus());
            while(jobExecution.isRunning()){
                System.out.println("...");
            }
        } catch (final JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (final JobRestartException e) {
            e.printStackTrace();
        } catch (final JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (final JobParametersInvalidException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        finally{
            return jobExecution != null ? jobExecution.getStatus() : BatchStatus.FAILED;
        }
    }
}
