package br.com.sicredi.sincronizacontas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sicredi.sincronizacontas.models.User;

@Repository
public interface UserBaseRepository extends JpaRepository<User, Long>{
    
}
