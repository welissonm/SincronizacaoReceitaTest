package br.com.sicredi.sincronizacontas.dto;

import br.com.sicredi.sincronizacontas.models.interfaces.IConta;

public class ContaDTO implements IConta{

    private int agencia;
    private String numero;
    private Float saldo;
    private char status;

    public ContaDTO(){
        this(0, "", 0.0F, '.');
    }


    public ContaDTO(int agencia, String numero, Float saldo, char status) {
        this.agencia = agencia;
        this.numero = numero;
        this.saldo = saldo;
        this.status = status;
    }
    
    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }


    @Override
    public int getAgencia() {
        return this.agencia;
    }

    public String getFromatedAgencia(){
        return String.format("%04d", this.agencia);
    }

    @Override
    public void setAgencia(int agencia) {
       this.agencia = agencia;
    }

    @Override
    public String getNumero() {
        return this.numero;
    }

    public String getFormatedNumero(){
        return  String.format("%06d", Integer.parseInt(numero.replace("-", "")));
    }

    @Override
    public void setNumero(String numero) {
     this.numero = numero;
    }


}
