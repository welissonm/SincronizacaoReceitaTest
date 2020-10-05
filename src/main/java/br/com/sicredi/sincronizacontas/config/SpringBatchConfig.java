package br.com.sicredi.sincronizacontas.config;

import java.io.File;
import java.io.IOException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

import br.com.sicredi.sincronizacontas.batch.JobCompletionNotificationListener;
import br.com.sicredi.sincronizacontas.dto.ContaDTO;
import br.com.sicredi.sincronizacontas.models.Conta;

@Configuration
@EnableBatchProcessing
// public class SpringBatchConfig extends DefaultBatchConfigurer {
public class SpringBatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
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

    //step para persistencia das contas em banco de dados
    @Bean
    @Profile("prodution")
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

    //step para persistencia das contas em arquivos
    @Bean
    public Step step2
    (
        ItemReader<ContaDTO> itemReader, 
        ItemProcessor<ContaDTO, ContaDTO> itemProcessor,
        ItemWriter<ContaDTO> itemWrite
    )
    {
        return stepBuilderFactory.get("contas-file-load")
            .<ContaDTO, ContaDTO>chunk(100)
            .reader(itemReader)
            .processor(itemProcessor)
            .writer(itemWrite)
            .build();
    }

    @Bean
    public FlatFileItemReader<ContaDTO>  fileItemReader(@Value("${input-file}") Resource resource) {
        FlatFileItemReader<ContaDTO>flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(this.lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public FlatFileItemWriter<ContaDTO> writerItemFile(@Value("${output-file}") Resource resource,
            FlatFileHeaderCallback headerCallback) {
        // Cria uma instancia write
        FlatFileItemWriter<ContaDTO> writer = new FlatFileItemWriter<>();
        writer.setHeaderCallback(headerCallback);
        if (!resource.exists()) {
            try {
                File file = resource.getFile();
                file.createNewFile();
            } catch (IOException e) {
                // TODO enviar log de erro e mensagem atraves do servico de mensageria
                e.printStackTrace();
            }
        }
        //Seta o local para o arquivo de saida
        writer.setResource(resource);
         
        //Todas as repetições do job devem "anexar" ao mesmo arquivo de saída
        writer.setAppendAllowed(true);
         
        //Sequencia de valores dos nomes dos campos com base nas propriedades do objeto
        writer.setLineAggregator(new DelimitedLineAggregator<ContaDTO>() {
            {
                setDelimiter(";");
                setFieldExtractor(new BeanWrapperFieldExtractor<ContaDTO>() {
                    {
                        setNames(new String[] {"agencia", "numero","saldo", "status", "sincronizado"});
                    }
                });
            }
        });
        return writer;
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
