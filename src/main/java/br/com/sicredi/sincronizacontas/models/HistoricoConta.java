package br.com.sicredi.sincronizacontas.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

@Entity
@Table(name = "tb_historico_contas", schema = "public")
public class HistoricoConta implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 9082482552959242011L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Float saldo;
    private char status;
    // sicronizado na Receita federal
    private boolean sincronizado;
    private Date data;
    private Date dataSicronizacao;
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    public HistoricoConta(){
        this(0.0F, '.', false, null, null);
    }
    
    public HistoricoConta(Float saldo, char status, boolean sincronizado, Date data, Date dataSicronizacao){
        this(0L, saldo, status, sincronizado, data, dataSicronizacao, null);
    }
    
    public HistoricoConta(Float saldo, char status, boolean sincronizado, Date data, Date dataSicronizacao,
            Conta conta) 
    {
        this(0L, saldo, status, sincronizado, data, dataSicronizacao, conta);
    }

    public HistoricoConta(Long id, Float saldo, char status, boolean sincronizado, Date data, Date dataSicronizacao,
    Conta conta) 
    {
        this.id = id;
        this.saldo = saldo;
        this.status = status;
        this.sincronizado = sincronizado;
        this.data = data;
        this.dataSicronizacao = dataSicronizacao;
        this.conta = conta;
    }

    public Long getId() {
        return id;
    }

    public boolean isSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(boolean sincronizado) {
        this.sincronizado = sincronizado;
    }

    public void setId(Long id) {
        this.id = id;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getDataSicronizacao() {
        return dataSicronizacao;
    }

    public void setDataSicronizacao(Date dataSicronizacao) {
        this.dataSicronizacao = dataSicronizacao;
    }

    
	public Conta getConta() {
		return this.conta;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((saldo == null) ? 0 : saldo.hashCode());
        result = prime * result + (sincronizado ? 1231 : 1237);
        result = prime * result + status;
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
        HistoricoConta other = (HistoricoConta) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (saldo == null) {
            if (other.saldo != null)
                return false;
        } else if (!saldo.equals(other.saldo))
            return false;
        if (sincronizado != other.sincronizado)
            return false;
        if (status != other.status)
            return false;
        return true;
    }

    



    

    

}
