package br.com.sicredi.sincronizacontas.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(
    name="tb_relatorio_contas",
    schema="public"
)
@AttributeOverride( name="id", column = @Column(name="id_relatorio"))
public class RelatorioConta implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 8963483699259580556L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "saldo", nullable = false, precision = 2)
    private Float saldo;

    @Column(name = "status", nullable = false)
    private char status;

    @Column(name = "sincronizado", nullable = false)
    private boolean sincronizado;

    @Column(nullable = false, updatable = false, name = "data")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @Column(nullable = true, updatable = false, name = "data_sincronizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSicronizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_conta", foreignKey = @ForeignKey(name = "fk_relatorio_conta_conta"), nullable = false)
    private Conta conta;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public RelatorioConta(){
        this(0.0F, '.', false, new Date(), null);
    }

    public RelatorioConta(Float saldo, char status, boolean sincronizado, Date data, Date dataSicronizacao) {
        this(0.0F, status, sincronizado, data, dataSicronizacao, null);
    }

    public RelatorioConta(Float saldo, char status, boolean sincronizado, Date data, Date dataSicronizacao, Conta conta) {
        this(0L, 0.0F, status, sincronizado, data, dataSicronizacao, conta);
    }

    public RelatorioConta(Long id, Float saldo, char status, boolean sincronizado, Date data, Date dataSicronizacao, Conta conta) {
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

    public boolean isSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(boolean sincronizado) {
        this.sincronizado = sincronizado;
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
    
    public void setConta(Conta conta) {
        this.conta = conta;
    }

    @Override
    public String toString() {
        return "RelatorioConta [data=" + data + ", dataSicronizacao=" + dataSicronizacao + ", id=" + id + ", saldo="
                + saldo + ", sincronizado=" + sincronizado + ", status=" + status + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((dataSicronizacao == null) ? 0 : dataSicronizacao.hashCode());
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
        RelatorioConta other = (RelatorioConta) obj;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (dataSicronizacao == null) {
            if (other.dataSicronizacao != null)
                return false;
        } else if (!dataSicronizacao.equals(other.dataSicronizacao))
            return false;
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
