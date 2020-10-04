package br.com.sicredi.sincronizacontas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sicredi.sincronizacontas.models.HistoricoConta;

@Repository
public interface HistoricoContaRepository extends JpaRepository<HistoricoConta, Long> {
    
}
