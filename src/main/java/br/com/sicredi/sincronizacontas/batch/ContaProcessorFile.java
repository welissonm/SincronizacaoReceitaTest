package br.com.sicredi.sincronizacontas.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sicredi.sincronizacontas.dto.ContaDTO;
import br.com.sicredi.sincronizacontas.services.ReceitaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processor para persistencia em arquivos
 */
@Component
public class ContaProcessorFile implements ItemProcessor<ContaDTO, ContaDTO> {


    @Autowired
    ReceitaService receitaService;

    private static final Logger log = LoggerFactory.getLogger(ContaProcessorFile.class);
    
    @Override
    public ContaDTO process(final ContaDTO contaDto) throws Exception{
        log.info(String.format("Processano %s", contaDto));
        boolean result = false;
        try{
            result = receitaService.atualizarConta(
                contaDto.getFromatedAgencia(), 
                contaDto.getFormatedNumero(), 
                contaDto.getSaldoDecimal().doubleValue(), 
                Character.toString(contaDto.getStatus())
            );
        }catch( Exception ex){
            log.error(ex.getMessage(), ex);
            //TODO: Colocar na fila de retentativas de um serviço de mensageria como, por exemplo, rabbitmq ou Kafka,
            //para que outra instancia do servico possa tentar sincronizar novamente.
        } finally {
            contaDto.setSincronizado(result);
        }
        return contaDto;
    }
}
