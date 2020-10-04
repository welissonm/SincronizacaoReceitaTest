package br.com.sicredi.sincronizacontas.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.sicredi.sincronizacontas.models.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    public default Iterable<Conta> find(List<Conta> list){
        // DetachedCriteria subquery = DetachedCriteria.forClass(Skill.class,"skill");
        ArrayList<Conta> contas = new ArrayList<>();
        for( Conta conta : list){
            Conta result = this.findByAgenciaAndNumero(conta.getAgencia(), conta.getNumero());
            if(result != null && result.equals(conta)){
                contas.add(result);
            }
        }
        return contas;
    }

    public Conta findByAgenciaAndNumero(int agencia, String numero);
}

