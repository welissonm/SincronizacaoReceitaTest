package br.com.sicredi.sincronizacontas.batchs;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sicredi.sincronizacontas.models.Conta;
import br.com.sicredi.sincronizacontas.services.ContaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DBItemWrite implements ItemWriter<Conta> {

    private static final Logger log = LoggerFactory.getLogger(DBItemWrite.class);
    
    @Autowired
    private ContaService contaService;

    @Override
    public void write(List<? extends Conta> contas) throws Exception {
        log.info("Data saved for Contas" + contas);
        Iterable<Conta> _contas = this.contaService.saveInBatch((List<Conta>)contas);
    }
    
}
