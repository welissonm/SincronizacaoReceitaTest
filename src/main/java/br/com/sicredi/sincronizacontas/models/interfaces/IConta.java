package br.com.sicredi.sincronizacontas.models.interfaces;

public interface IConta {
    
    public int getAgencia();
    
    public void setAgencia(int agencia);

    public Float getSaldo();

    public void setSaldo(Float saldo);

    public char getStatus();

    public void setStatus(char status);

    public String getNumero();

    public void setNumero(String numero);
}
