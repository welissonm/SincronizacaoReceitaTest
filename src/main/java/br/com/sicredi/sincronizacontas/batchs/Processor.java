package br.com.sicredi.sincronizacontas.batchs;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sicredi.sincronizacontas.dto.ContaDTO;
import br.com.sicredi.sincronizacontas.models.Conta;
import br.com.sicredi.sincronizacontas.models.RelatorioConta;
import br.com.sicredi.sincronizacontas.services.ContaService;
import br.com.sicredi.sincronizacontas.services.ReceitaService;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Processor implements ItemProcessor<ContaDTO, Conta> {

    @Autowired
    private ContaService contaService;
    @Autowired
    private ReceitaService receitaService;

    private static final Logger log = LoggerFactory.getLogger(Processor.class);
    
    @Override
    public Conta process(ContaDTO contaDto) throws Exception{
        log.info(String.format("Processano %s", contaDto));
        Conta conta = this.contaService.find(new Conta(0L, contaDto.getAgencia(), contaDto.getNumero()));
        conta = conta != null ? conta : new Conta(0L, contaDto.getAgencia(), contaDto.getNumero());
        RelatorioConta relatorioConta = new RelatorioConta(contaDto.getSaldo(), contaDto.getStatus(), false, new Date(), null);
        boolean result = false;
        try{
            result = receitaService.atualizarConta(
                contaDto.getFromatedAgencia(), 
                contaDto.getFormatedNumero(), 
                contaDto.getSaldo().doubleValue(), 
                Character.toString(contaDto.getStatus())
            );
        }catch( Exception ex){
            log.error(ex.getMessage(), ex);
            //TODO: Colocar na fila de retentativas de um serviço de mensageria como, por exemplo, rabbitmq ou Kafka,
            //para que outra instancia do servico possa tentar sincronizar novamente.
        } finally {
            // conta.setSincronizado(result);
            relatorioConta.setSincronizado(result);
            relatorioConta.setDataSicronizacao(new Date());
            conta.getRelatorioConta().add(relatorioConta);
            relatorioConta.setConta(conta);
        }
        return conta;
    }
}
