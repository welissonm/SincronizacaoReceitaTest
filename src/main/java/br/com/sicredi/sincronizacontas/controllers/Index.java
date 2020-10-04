package br.com.sicredi.sincronizacontas.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/")
public class Index {
    
    @GetMapping()
    public @ResponseStatus HttpStatus index(){
        return HttpStatus.OK;
    }
}
