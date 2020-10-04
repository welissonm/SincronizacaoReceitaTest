package br.com.sicredi.sincronizacontas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sicredi.sincronizacontas.models.Conta;
import br.com.sicredi.sincronizacontas.repositories.ContaRepository;

@Service
public class ContaService {

    @Autowired
    private ContaRepository ContaRepostiry;

    public Iterable<Conta> listAll(){
        return this.ContaRepostiry.findAll();
    }

    public Iterable<Conta> find(List<Conta> list){
        return this.ContaRepostiry.find(list);
    }

    public Conta find(Conta conta){
        return this.ContaRepostiry.find(conta).get();
    }
}
