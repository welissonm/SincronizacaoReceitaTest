package br.com.sicredi.sincronizacontas.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import br.com.sicredi.sincronizacontas.models.Conta;

@Configuration
@EnableBatchProcessing
// public class SpringBatchConfig extends DefaultBatchConfigurer {
public class SpringBatchConfig {
    
    @Bean
    public Job job(
        JobBuilderFactory jobBuilderFactory, 
        StepBuilderFactory stepBuilderFactory, 
        ItemReader<Conta> itemReader,
        ItemProcessor<Conta,Conta> itemProcessor,
        ItemWriter<? super Conta> itemWrite)
    {
        Step step = stepBuilderFactory.get("TEL-file-load")
                    .<Conta, Conta>chunk(100)
                    .reader(itemReader)
                    .processor(itemProcessor)
                    .writer(itemWrite)
                    .build();
        return jobBuilderFactory.get("ETL-Load")
        .incrementer(new RunIdIncrementer())
        .start(step)
        .build();
    }

    @Bean
    public FlatFileItemReader<Conta> fileItemReader(@Value("${input}") Resource resource){
        FlatFileItemReader<Conta> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(this.lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<Conta> lineMapper(){
        DefaultLineMapper<Conta> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(";");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[] {"agencia", "numero","saldo", "status"});
        BeanWrapperFieldSetMapper<Conta> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Conta.class);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return defaultLineMapper;
    }
}
