package br.com.sicredi.sincronizacontas;

import java.util.HashMap;
import java.util.Map;

import javax.batch.operations.JobRestartException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.sicredi.sincronizacontas.config.TerminateBean;
import br.com.sicredi.sincronizacontas.services.JobService;

@SpringBootApplication(scanBasePackages = {"br.com.sicredi.sincronizacontas"})
@EnableAutoConfiguration
@EntityScan(basePackages = {"br.com.sicredi.sincronizacontas"})
@EnableJpaRepositories(value = {"br.com.sicredi.sincronizacontas"})
@EnableScheduling
public class SincronizacontasApplication {

	@Autowired
	JobService jobService;

	private static final Logger log = LoggerFactory.getLogger(SincronizacontasApplication.class);
	public static void main(String[] args) {
		// ApplicationContext context = SpringApplication.run(SincronizacontasApplication.class, args);
		ConfigurableApplicationContext context = new 
		SpringApplicationBuilder(SincronizacontasApplication.class).web(WebApplicationType.SERVLET).run(args);
		InputFileCheck _inputFileBean = context.getBean(InputFileCheck.class);
		context.getBean(TerminateBean.class);
        if(!_inputFileBean.exist()){
			context.close();
			System.err.println(String.format("ERROR: O arquivo %s não foi encontrado", _inputFileBean.resource.getFilename()));
			System.exit(-1);
		} else{
			SincronizacontasApplication.jobRunner(context.getBean(JobService.class));
		}
	}

	@Bean
    InputFileCheck inputFileBean() {
        return new InputFileCheck();
    }

	private static class InputFileCheck {
		
		@Value("${input-file}")
        private Resource resource;

        public boolean exist() {
            System.out.printf("The value of application arg input: %s%n", resource.getFilename());
			return resource.exists();
        }
	}

	@Scheduled(cron = "0 0 5 ? * *")
    public void perform() throws Exception 
	{
		SincronizacontasApplication.jobRunner(jobService);
	}
	
	private static void jobRunner(JobService jobService)
    {
		final Map<String, JobParameter> mapParams = new HashMap<>();
        mapParams.put("time", new JobParameter(System.currentTimeMillis()));
        // final JobParameters jobParameters = new JobParameters(mapParams);
        JobExecution jobExecution = null;
        try {
            // jobExecution = jobLauncher.run(job, jobParameters);
			jobExecution = jobService.createJob(mapParams);
            System.out.println("Jobexecution ID " + jobExecution.getJobId() + " status " + jobExecution.getStatus());
            while(jobExecution.isRunning()){
                System.out.println("...");
            }
		} catch (org.springframework.batch.core.repository.JobRestartException e) {
			//TODO: pra cada tipo de excecao, lancar no servico de log e de mensageria pra registart o status
			e.printStackTrace();
		} 
		catch (final JobExecutionAlreadyRunningException e) {
			//TODO: pra cada tipo de excecao, lancar no servico de log e de mensageria pra registart o status
            e.printStackTrace();
        } catch (final JobRestartException e) {
            e.printStackTrace();
        } catch (final JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (final JobParametersInvalidException e) {
            e.printStackTrace();
        }
        finally{
            log.info("job status od the conclusion ");
		}
	}

}
