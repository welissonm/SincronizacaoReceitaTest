package br.com.sicredi.sincronizacontas.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.sicredi.sincronizacontas.models.Conta;


@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

  @Override
  public void afterJob(JobExecution jobExecution) {
    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");

      // TODO: nesse trecho e enviado um log para o servico de log informando que
      // o processamento foi concluido com sucesso pelo batch
      //TODO: Nesse trecho tambem sera incluido uma envio de mensagem para notificar 
      //os servicos interessados na conclusao do processo da demanda pelo batch com sucesso
      jdbcTemplate.query("SELECT * FROM public.tb_contas",
        (rs, row) -> new Conta(
          rs.getLong(1),
          rs.getInt(2),
          rs.getString(3))
      ).forEach(conta -> log.info("Found <" + conta + "> in the database."));
      //TODO: end
      //TODO: end
    } else if(jobExecution.getStatus() == BatchStatus.FAILED) {
      // TODO: nesse trecho e enviado um log para o servico de log informando que
      // ocorreu no processamento pelo batch
      //TODO: Nesse trecho tambem sera incluido uma envio de mensagem para notificar 
      //os servicos interessados que ocorreu um erro no processamento da demanda pelo
      //batch
      log.error("!!! JOB FAILED!");
    }
    //TODO: envio de logs ou de mensagens para os seus respectivos servicos, avisando 
    // aos demais servicos interessados, o estado atual do job.
  }
}
