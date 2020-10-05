package br.com.sicredi.sincronizacontas.dto;

/**
 * Data Transfer Object do tipo conta
 */
public class ContaDTO {

    private int agencia;
    private String numero;
    private String saldo;
    private char status;
    private boolean sincronizado;

    public ContaDTO(){
        this(0, "", "0,0", '.');
    }


    public ContaDTO(int agencia, String numero, String saldo, char status) {
        this.agencia = agencia;
        this.numero = numero;
        this.saldo = saldo;
        this.status = status;
    }
    
    public String getSaldo() {
        return this.saldo;
    }

    public Float getSaldoDecimal(){
        return Float.parseFloat(this.saldo.replace(',', '.'));
    }

    public void setSaldo(Float saldo){
        this.setSaldo(saldo.toString());
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo.replace('.', ',');

    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }


    public int getAgencia() {
        return this.agencia;
    }

    public String getFromatedAgencia(){
        return String.format("%04d", this.agencia);
    }

    public void setAgencia(int agencia) {
       this.agencia = agencia;
    }

    public String getNumero() {
        return this.numero;
    }

    public String getFormatedNumero(){
        return  String.format("%06d", Integer.parseInt(numero.replace("-", "")));
    }

    public void setNumero(String numero) {
     this.numero = numero;
    }

    public boolean isSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(boolean sincronizado) {
        this.sincronizado = sincronizado;
    }

    
    @Override
    public String toString() {
        final StringBuffer strBuffer = new StringBuffer("Conta{");
        strBuffer.append("agencia=").append(agencia).append(',');
        strBuffer.append("numero=").append(numero).append(',');
        strBuffer.append("saldo=").append(saldo).append(',');
        strBuffer.append("status=").append(status).append(',');
        strBuffer.append("sincronizado=").append(sincronizado).append('}');
        return strBuffer.toString();
    }


}
