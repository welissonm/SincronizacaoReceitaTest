package br.com.sicredi.sincronizacontas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShutdownConfig {
 
    @Bean
    public TerminateBean getTerminateBean() {
        return new TerminateBean();
    }
}