package br.com.sicredi.sincronizacontas.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.sicredi.sincronizacontas.models.User;

public interface UserBaseRepository extends CrudRepository<User, Long>{
    
}
