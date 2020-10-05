package br.com.sicredi.sincronizacontas.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import br.com.sicredi.sincronizacontas.dto.ContaDTO;
import br.com.sicredi.sincronizacontas.models.Conta;

@Configuration
@EnableBatchProcessing
// @Import(DBContext.class)
public class SpringBatchConfig extends DefaultBatchConfigurer {
    // public class SpringBatchConfig {

        
  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;

    // @Bean
	// public ResourcelessTransactionManager transactionManager() {
	// 	return new ResourcelessTransactionManager();
    // }
    
    @Bean
    public JobRepositoryFactoryBean jobRepositoryFactory (DataSource dataSource, 
    PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        // factory.setDatabaseType("POSTGRES");
        factory.setTransactionManager(transactionManager);
		factory.afterPropertiesSet();
		return factory;
    }
    
    // @Bean
	// public JobRepository jobRepository(JobRepositoryFactoryBean factory) throws Exception {
	// 	return factory.getObject();
    // }
  
    @Bean
    public Job job( JobCompletionNotificationListener listener, Step step)
    {
        return jobBuilderFactory.get("Sincronizar Contas")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step)
            .end()
            .build();
    }

    @Bean
    public Step step
    (
        ItemReader<ContaDTO> itemReader, 
        ItemProcessor<ContaDTO, Conta> itemProcessor,
        ItemWriter<Conta> itemWrite
    )
    {
        return stepBuilderFactory.get("contas-file-load")
            .<ContaDTO, Conta>chunk(100)
            .reader(itemReader)
            .processor(itemProcessor)
            .writer(itemWrite)
            .build();
    }

    @Bean
    public FlatFileItemReader<ContaDTO> fileItemReader(@Value("${input}") Resource resource) throws IOException {
        FlatFileItemReader<ContaDTO> flatFileItemReader = new FlatFileItemReader<>();
        // ClassPathResource classPathResource = new ClassPathResource(resource.getURL().toString());
        // flatFileItemReader.setResource(classPathResource);
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(this.lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<ContaDTO> lineMapper(){
        DefaultLineMapper<ContaDTO> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(";");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[] {"agencia", "numero","saldo", "status"});
        BeanWrapperFieldSetMapper<ContaDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ContaDTO.class);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return defaultLineMapper;
    }
}
