package br.com.sicredi.sincronizacontas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"br.com.sicredi.sincronizacontas"})
@EnableAutoConfiguration
@EntityScan(basePackages = {"br.com.sicredi.sincronizacontas"})
@EnableJpaRepositories(value = {"br.com.sicredi.sincronizacontas"})
public class SincronizacontasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SincronizacontasApplication.class, args);
	}

}
