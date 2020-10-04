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
            Optional<Conta> result = this.find(conta);
            if(!result.isEmpty() && result.equals(conta)){
                contas.add(result.get());
            }
        }
        return contas;
    }

    public default Optional<Conta> find(Conta conta){
        Example<Conta> example = Example.of(conta, ExampleMatcher.matching());
        Optional<Conta> result = this.findOne(example);
        return result;
    }
}

