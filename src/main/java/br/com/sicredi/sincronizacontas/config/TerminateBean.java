package br.com.sicredi.sincronizacontas.config;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerminateBean  {

    private static final Logger log = LoggerFactory.getLogger(TerminateBean.class);
    
    @PreDestroy
    public void onDestroy() throws Exception {
        log.info("Spring Application is destroyed!");
    }
}
