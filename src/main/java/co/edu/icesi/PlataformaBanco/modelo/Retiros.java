package co.edu.icesi.PlataformaBanco.modelo;
// Generated 10/04/2018 03:15:32 PM by Hibernate Tools 5.2.8.Final

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Retiros generated by hbm2java
 */
@Entity
@Table(name = "retiros", schema = "public")
public class Retiros implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3944895206756479914L;
	private RetirosId id;
	private Cuentas cuentas;
	private Usuarios usuarios;
	private BigDecimal retValor;
	private Date retFecha;
	private String retDescripcion;

	public Retiros() {
	}

	public Retiros(RetirosId id, Cuentas cuentas, BigDecimal retValor, Date retFecha, String retDescripcion) {
		this.id = id;
		this.cuentas = cuentas;
		this.retValor = retValor;
		this.retFecha = retFecha;
		this.retDescripcion = retDescripcion;
	}

	public Retiros(RetirosId id, Cuentas cuentas, Usuarios usuarios, BigDecimal retValor, Date retFecha,
			String retDescripcion) {
		this.id = id;
		this.cuentas = cuentas;
		this.usuarios = usuarios;
		this.retValor = retValor;
		this.retFecha = retFecha;
		this.retDescripcion = retDescripcion;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "retCodigo", column = @Column(name = "ret_codigo", nullable = false, precision = 10, scale = 0)),
			@AttributeOverride(name = "cueNumero", column = @Column(name = "cue_numero", nullable = false, length = 30)) })
	public RetirosId getId() {
		return this.id;
	}

	public void setId(RetirosId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cue_numero", nullable = false, insertable = false, updatable = false)
	public Cuentas getCuentas() {
		return this.cuentas;
	}

	public void setCuentas(Cuentas cuentas) {
		this.cuentas = cuentas;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usu_cedula")
	public Usuarios getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

	@Column(name = "ret_valor", nullable = false, precision = 10)
	public BigDecimal getRetValor() {
		return this.retValor;
	}

	public void setRetValor(BigDecimal retValor) {
		this.retValor = retValor;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ret_fecha", nullable = false, length = 29)
	public Date getRetFecha() {
		return this.retFecha;
	}

	public void setRetFecha(Date retFecha) {
		this.retFecha = retFecha;
	}

	@Column(name = "ret_descripcion", nullable = false, length = 50)
	public String getRetDescripcion() {
		return this.retDescripcion;
	}

	public void setRetDescripcion(String retDescripcion) {
		this.retDescripcion = retDescripcion;
	}

}
