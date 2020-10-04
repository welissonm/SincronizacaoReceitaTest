package br.com.sicredi.sincronizacontas.models;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.sicredi.sincronizacontas.models.interfaces.IConta;

@Entity
@Table(
    name="tb_contas",
    schema="public",
    uniqueConstraints = @UniqueConstraint(columnNames = { "agencia", "numero" })
)
@AttributeOverride( name="id", column = @Column(name="id_conta"))
public class Conta implements IConta, java.io.Serializable  {

    /**
     *
     */
    private static final long serialVersionUID = 7710221601005398013L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agencia", nullable = false)
    private int agencia;

    @Column(name="numero", nullable = false)
    private String numero;
    
    // @OneToMany
    // @JoinColumn(name = "id_conta")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "conta")
    private List<RelatorioConta> relatorioConta;

    public Conta(){
        this(0L, 0, "");
    }

    public Conta(Long id, int agencia, String numero){
        this.id = id;
        this.agencia = agencia;
        this.numero = numero;
        this.relatorioConta = new ArrayList<>();
    }

    
    public long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
	public List<RelatorioConta> getRelatorioConta() {
		return this.relatorioConta;
    }

    public void setRelatorioConta(List<RelatorioConta> relatorioConta) {
        this.relatorioConta = relatorioConta;
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
    public String toString() {
        final StringBuffer strBuffer = new StringBuffer("Conta{");
        if(this.id > 0L){
            strBuffer.append("id=").append(id).append(',');
        }
        strBuffer.append("agencia=").append(agencia).append(',');
        strBuffer.append("numero=").append(numero).append('}');
        return strBuffer.toString();
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
    
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
