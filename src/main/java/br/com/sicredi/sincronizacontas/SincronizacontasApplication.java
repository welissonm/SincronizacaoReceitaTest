package br.com.sicredi.sincronizacontas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.sicredi.sincronizacontas.config.TerminateBean;

@SpringBootApplication(scanBasePackages = {"br.com.sicredi.sincronizacontas"})
@EnableAutoConfiguration
@EntityScan(basePackages = {"br.com.sicredi.sincronizacontas"})
@EnableJpaRepositories(value = {"br.com.sicredi.sincronizacontas"})
public class SincronizacontasApplication {

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

}
