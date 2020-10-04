package br.com.sicredi.sincronizacontas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.sicredi.sincronizacontas.models.Conta;
import br.com.sicredi.sincronizacontas.services.ContaService;

@RestController
@RequestMapping("/contas")
public class ContaController {
    
    @Autowired
    private ContaService contaService;
    
    @GetMapping
    public @ResponseBody Iterable<Conta> listAll(){
        Iterable<Conta> list = contaService.listAll();
        return list;
    }
}
