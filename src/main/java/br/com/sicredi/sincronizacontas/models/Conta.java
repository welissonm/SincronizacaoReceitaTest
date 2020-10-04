package br.com.sicredi.sincronizacontas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.sicredi.sincronizacontas.models.interfaces.IConta;

@Entity
@Table(name="tb_contas", schema="public")
public class Conta implements IConta{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int agencia;
    // private int numero;
    // private char digito;
    private String numero;
    private Float saldo;
    private char status;
    //sicronizado na Receita federal
    private boolean sincronizado;

    public Conta(){
        this(0L, 0, "", 0.0F, '.', false);
    }

    // public Conta(Long id, int agencia, int numero, char digito, Float saldo, char status){
    public Conta(Long id, int agencia, String numero, Float saldo, char status, boolean sincronizado){
        this.id = id;
        this.agencia = agencia;
        // this.numero = numero;
        // this.digito = digito;
        this.numero = numero;
        this.saldo = saldo;
        this.status = status;
        this.sincronizado = sincronizado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAgencia() {
        return agencia;
    }

    public String getFromatedAgencia(){
        return String.format("%04d", this.agencia);
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    // public int getNumero() {
    //     return numero;
    // }

    // public void setNumero(int numero) {
    //     this.numero = numero;
    // }

    // public char getDigito() {
    //     return digito;
    // }

    // public void setDigito(char digito) {
    //     this.digito = digito;
    // }

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

    public String getNumero() {
        return numero;
    }

    public String getFormatedNumero(){
        return  String.format("%06d", Integer.parseInt(numero.replace("-", "")));
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        final StringBuffer strBuffer = new StringBuffer("Conta{");
        if(this.id > 0L){
            strBuffer.append("id=").append(id).append(',');
        }
        strBuffer.append("agencia=").append(agencia).append(',');
        strBuffer.append("numero=").append(numero).append(',');
        strBuffer.append("saldo=").append(saldo).append(',');
        strBuffer.append("status=").append(status).append('}');
        return strBuffer.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + agencia;
        result = prime * result + ((numero == null) ? 0 : numero.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Conta other = (Conta) obj;
        if (agencia != other.agencia)
            return false;
        if (numero == null) {
            if (other.numero != null)
                return false;
        } else if (!numero.equals(other.numero))
            return false;
        return true;
    }

    public boolean isSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(boolean sincronizado) {
        this.sincronizado = sincronizado;
    }

    
    
    

}
