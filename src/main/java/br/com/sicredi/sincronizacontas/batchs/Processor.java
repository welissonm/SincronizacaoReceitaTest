package br.com.sicredi.sincronizacontas.batchs;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sicredi.sincronizacontas.models.Conta;
import br.com.sicredi.sincronizacontas.services.ContaService;
import br.com.sicredi.sincronizacontas.services.ReceitaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Processor implements ItemProcessor<Conta, Conta> {

    // @Autowired
    // private ContaService contaService;
    @Autowired
    ReceitaService receitaService;

    private static final Logger log = LoggerFactory.getLogger(Processor.class);
    
    @Override
    public Conta process(Conta conta) throws Exception{
        log.info(String.format("Processano %s", conta));
        // Conta result = contaService.find(conta);
        // return result != null ? result : conta;
        boolean result = false;
        try{
            result = receitaService.atualizarConta(conta.getFromatedAgencia(), conta.getFormatedNumero(), conta.getSaldo().doubleValue(), Character.toString(conta.getStatus()));
        }catch( Exception ex){
            log.error(ex.getMessage(), ex);
            //TODO: Colocar na fila de retentativas de um serviço de mensageria como, por exemplo, rabbitmq ou Kafka,
            //para que outra instancia do servico possa tentar sincronizar novamente.
        } finally {
            conta.setSincronizado(result);
        }
        return conta;
    }
}
