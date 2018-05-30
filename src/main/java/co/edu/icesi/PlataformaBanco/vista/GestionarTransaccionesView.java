package co.edu.icesi.PlataformaBanco.vista;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;

import co.edu.icesi.PlataformaBanco.businessDelegate.IBusinessDelegate;
import co.edu.icesi.PlataformaBanco.modelo.Consignaciones;
import co.edu.icesi.PlataformaBanco.modelo.ConsignacionesId;
import co.edu.icesi.PlataformaBanco.modelo.Cuentas;
import co.edu.icesi.PlataformaBanco.modelo.Retiros;
import co.edu.icesi.PlataformaBanco.modelo.RetirosId;
import co.edu.icesi.PlataformaBanco.modelo.Transferencias;
import co.edu.icesi.PlataformaBanco.modelo.TransferenciasId;
import co.edu.icesi.PlataformaBanco.modelo.Usuarios;

@ManagedBean
@ViewScoped
public class GestionarTransaccionesView {

	@ManagedProperty(value = "#{businessDelegate}")
	private IBusinessDelegate businessDelegate;

	// Indice para manejar la tabla
	private int tanIndex;
	// Usuario que est� en session
	private String usuario;

	// Atributos consignacion
	private InputText txtNumeroCuenta_consignacion;
	private InputText txtCedulaUsuario_consignacion;
	private long cedulaUsuario_consignacion;
	private BigDecimal valorConsignacion;
	private InputText txtValorConsigancion;
	private InputTextarea descripcion_consignacion;
	private CommandButton btnGuardar_consignacion;
	private CommandButton btnLimpiar_consignacion;
	private List<Consignaciones> consignaciones;

	// Atributos retiros
	private InputText txtNumeroCuenta_Retiro;
	private InputText txtCedulaUsuario_Retiro;
	private long cedulaUsuario_Retiro;
	private BigDecimal valorRetiro;
	private InputText txtValorRetiro;
	private InputTextarea descripcion_Retiro;
	private CommandButton btnGuardar_Retiro;
	private CommandButton btnLimpiar_Retiro;
	private List<Retiros> retiros;

	// Atributos transferencias
	private InputText txtNumeroCuenta_origen_Transferencia;
	private InputText txtNumeroCuenta_destino_Transferencia;
	private InputText txtCedulaUsuario_Transferencia;
	private long cedulaUsuario_Transferencia;
	private BigDecimal valorTransferencia;
	private InputText txtValorTransferencia;
	private InputTextarea descripcion_Transferencia;
	private CommandButton btnGuardar_Transferencia;
	private CommandButton btnLimpiar_Transferencia;
	private List<Transferencias> transferencias;

	@PostConstruct
	public void recuperarUsuario() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		usuario = (String)session.getAttribute("usuario");
	}
	
	public void txtNumeroCuentaListener_consignacion() {
		try {
			// Se busca la cuenta
			Cuentas cuenta = businessDelegate.findCuentaByID(txtNumeroCuenta_consignacion.getValue().toString());
			if (cuenta == null)
				throw new Exception("La cuenta no existe, ingrese un numero de cuenta válido");
			// Se acomodan los campos para crear consignacion
			btnGuardar_consignacion.setDisabled(false);
			txtCedulaUsuario_consignacion.setValue(usuario);
			txtValorConsigancion.setDisabled(false);
			descripcion_consignacion.setDisabled(false);
			tanIndex = 0;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}
	}

	public void txtNumeroCuentaListener_retiro() {
		try {
			// Se busca la cuenta
			Cuentas cuenta = businessDelegate.findCuentaByID(txtNumeroCuenta_Retiro.getValue().toString());
			if (cuenta == null)
				throw new Exception("La cuenta no existe, ingrese un numero de cuenta válido");
			// Se acomodan los campos para crear consignacion
			btnGuardar_Retiro.setDisabled(false);
			txtCedulaUsuario_Retiro.setValue(usuario);
			txtValorRetiro.setDisabled(false);
			descripcion_Retiro.setDisabled(false);
			tanIndex = 1;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}
	}

	public void txtNumeroCuentaListener_transferencia() {
		try {
			// Se busca la cuenta de origen
			Cuentas cuentaOr = businessDelegate.findCuentaByID(txtNumeroCuenta_origen_Transferencia.getValue().toString());
			if (cuentaOr == null)
				throw new Exception("La cuenta de origen no existe, ingrese un numero de cuenta de origen válido");
			//Se revisa si ya se lleno la cuenta de destino
			if(txtNumeroCuenta_destino_Transferencia ==null
					||txtNumeroCuenta_destino_Transferencia.getValue()==null 
					||txtNumeroCuenta_destino_Transferencia.getValue().toString().trim().equals(""))
				throw new Exception("Ingrese el numero de cuenta destino");
			//Se busca la cuenta de detino
			Cuentas cuentaDes = businessDelegate.findCuentaByID(txtNumeroCuenta_destino_Transferencia.getValue().toString());
			if (cuentaDes == null)
				throw new Exception("La cuenta de origen no existe, ingrese un numero de cuenta de origen válido");
			// Se acomodan los campos para crear consignacion
			btnGuardar_Transferencia.setDisabled(false);
			txtCedulaUsuario_Transferencia.setValue(usuario);
			txtValorTransferencia.setDisabled(false);
			descripcion_Transferencia.setDisabled(false);
			tanIndex = 2;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}
	}

	public String action_guardarConsignacion() {
		// Se crea la consignacion
		Consignaciones consignacion = new Consignaciones();
		// Se crea el id de la consignacion
		ConsignacionesId consignacionId = new ConsignacionesId();
		consignacionId.setCueNumero(txtNumeroCuenta_consignacion.getValue().toString());
		consignacion.setId(consignacionId);
		try {
			// Se busca la cuenta de la la consignacion
			Cuentas cuenta = businessDelegate.findCuentaByID(txtNumeroCuenta_consignacion.getValue().toString().trim());
			consignacion.setCuentas(cuenta);
			// Se busca el usuario que realizara la consignacion
			Usuarios usuario = businessDelegate.findUsuarioById(cedulaUsuario_consignacion);
			consignacion.setUsuarios(usuario);
			// Se asigna el valor de la consignacion
			consignacion.setConValor(valorConsignacion);
			// Se asigna la fecha de la consignacion
			consignacion.setConFecha(new Date());
			// Se agrega la descripción a la consignacion
			consignacion.setConDescripcion(descripcion_consignacion.getValue().toString());
			businessDelegate.crearConsigancion(consignacion);
			consignaciones = null;
			action_limpiarConsignacion();
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado la consignación exitosamente", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}

		descripcion_consignacion.resetValue();
		return "";
	}

	public String action_limpiarConsignacion() {
		txtNumeroCuenta_consignacion.resetValue();
		cedulaUsuario_consignacion = 0;
		valorConsignacion = null;
		descripcion_consignacion.resetValue();
		btnGuardar_consignacion.setDisabled(true);
		txtValorConsigancion.setDisabled(true);
		descripcion_consignacion.setDisabled(true);
		return "";
	}

	public String action_guardarRetiro() {
		// Se crea el retiro
		Retiros retiro = new Retiros();
		// Se crea el id del retiro
		RetirosId retirosId = new RetirosId();
		retirosId.setCueNumero(txtNumeroCuenta_Retiro.getValue().toString());
		retiro.setId(retirosId);
		try {
			// Se busca la cuenta del retiro
			Cuentas cuenta = businessDelegate.findCuentaByID(txtNumeroCuenta_Retiro.getValue().toString());
			retiro.setCuentas(cuenta);
			// Se busca el usuario que realizara el retiro
			Usuarios usuario = businessDelegate.findUsuarioById(cedulaUsuario_Retiro);
			retiro.setUsuarios(usuario);
			// Se asigna el valor de la consignacion
			retiro.setRetValor(valorRetiro);
			// Se asigna la fecha de la consignacion
			retiro.setRetFecha(new Date());
			// Se agrega la descripción a la consignacion
			String observacion =descripcion_Retiro.getValue().toString();
			descripcion_Retiro.setValue(observacion);
			retiro.setRetDescripcion(observacion);
			businessDelegate.crearRetiro(retiro);
			retiros = null;
			action_limpiarRetiro();
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado el retiro exitosamente", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}
		return "";
	}

	public String action_limpiarRetiro() {
		txtNumeroCuenta_Retiro.resetValue();
		cedulaUsuario_Retiro = 0;
		valorRetiro = null;
		descripcion_Retiro.resetValue();
		btnGuardar_Retiro.setDisabled(true);
		txtValorRetiro.setDisabled(true);
		descripcion_Retiro.setDisabled(true);
		return "";
	}

	public String action_guardarTransferencia() {
		// Se crea la transferencia
		Transferencias transferencia = new Transferencias();
		// Se crea el id de la transferencia
		TransferenciasId transId = new TransferenciasId();
		transId.setOrigenCueNumero(txtNumeroCuenta_origen_Transferencia.getValue().toString());
		transId.setDestinoCueNumero(txtNumeroCuenta_destino_Transferencia.getValue().toString());
		transferencia.setId(transId);
		try {
			// Se busca la cuenta de origen de la transfrenecia
			Cuentas cuentaOrigen = businessDelegate
					.findCuentaByID(txtNumeroCuenta_origen_Transferencia.getValue().toString());
			transferencia.setCuentasByOrigenCueNumero(cuentaOrigen);
			// Se busca la cuenta de destino de la transfrenecia
			Cuentas cuentaDestino = businessDelegate
					.findCuentaByID(txtNumeroCuenta_destino_Transferencia.getValue().toString());
			transferencia.setCuentasByDestinoCueNumero(cuentaDestino);
			// Se busca el usuario que realizara la transferencia
			Usuarios usuario = businessDelegate.findUsuarioById(cedulaUsuario_Transferencia);
			if (usuario == null)
				throw new Exception("El usuario no existe");
			transferencia.setUsuCedula(cedulaUsuario_Transferencia);
			// Se asigna el valor de la consignacion
			transferencia.setTranValor(valorTransferencia);
			// Se asigna la fecha de la consignacion
			transferencia.setTranFecha(new Date());
			// Se agrega la descripción a la consignacion
			String observacion =descripcion_Transferencia.getValue().toString();
			transferencia.setTranDescripcion(observacion);
			descripcion_Transferencia.setValue(observacion);
			businessDelegate.crearTransferencia(transferencia);
			action_limpiarTransferencia();
			transferencias = null;
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado la transferencia exitosamente", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}
		return "";
	}

	public String action_limpiarTransferencia() {
		txtNumeroCuenta_origen_Transferencia.resetValue();
		txtNumeroCuenta_destino_Transferencia.resetValue();
		cedulaUsuario_Transferencia = 0;
		valorTransferencia = null;
		descripcion_Transferencia.resetValue();
		btnGuardar_Transferencia.setDisabled(true);
		txtValorTransferencia.setDisabled(true);
		descripcion_Transferencia.setDisabled(true);
		return "";
	}

	public List<Consignaciones> getConsignaciones() {
		if (consignaciones == null) {
			try {
				consignaciones = businessDelegate.findAllConsiganciones();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return consignaciones;
	}

	public void setConsignaciones(List<Consignaciones> consignaciones) {
		this.consignaciones = consignaciones;
	}

	public List<Retiros> getRetiros() {
		if (retiros == null) {
			try {
				retiros = businessDelegate.findAllRetiros();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retiros;
	}

	public void setRetiros(List<Retiros> retiros) {
		this.retiros = retiros;
	}

	public List<Transferencias> getTransferencias() {
		if (transferencias == null) {
			try {
				transferencias = businessDelegate.findAllTransferencia();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return transferencias;
	}

	public void setTransferencias(List<Transferencias> transferencias) {
		this.transferencias = transferencias;
	}

	public InputText getTxtNumeroCuenta_origen_Transferencia() {
		return txtNumeroCuenta_origen_Transferencia;
	}

	public void setTxtNumeroCuenta_origen_Transferencia(InputText txtNumeroCuenta_origen_Transferencia) {
		this.txtNumeroCuenta_origen_Transferencia = txtNumeroCuenta_origen_Transferencia;
	}

	public InputText getTxtNumeroCuenta_destino_Transferencia() {
		return txtNumeroCuenta_destino_Transferencia;
	}

	public void setTxtNumeroCuenta_destino_Transferencia(InputText txtNumeroCuenta_destino_Transferencia) {
		this.txtNumeroCuenta_destino_Transferencia = txtNumeroCuenta_destino_Transferencia;
	}

	public InputText getTxtCedulaUsuario_Transferencia() {
		return txtCedulaUsuario_Transferencia;
	}

	public void setTxtCedulaUsuario_Transferencia(InputText txtCedulaUsuario_Transferencia) {
		this.txtCedulaUsuario_Transferencia = txtCedulaUsuario_Transferencia;
	}

	public long getCedulaUsuario_Transferencia() {
		return cedulaUsuario_Transferencia;
	}

	public void setCedulaUsuario_Transferencia(long cedulaUsuario_Transferencia) {
		this.cedulaUsuario_Transferencia = cedulaUsuario_Transferencia;
	}

	public BigDecimal getValorTransferencia() {
		return valorTransferencia;
	}

	public void setValorTransferencia(BigDecimal valorTransferencia) {
		this.valorTransferencia = valorTransferencia;
	}

	public InputText getTxtValorTransferencia() {
		return txtValorTransferencia;
	}

	public void setTxtValorTransferencia(InputText txtValorTransferencia) {
		this.txtValorTransferencia = txtValorTransferencia;
	}

	public InputTextarea getDescripcion_Transferencia() {
		return descripcion_Transferencia;
	}

	public void setDescripcion_Transferencia(InputTextarea descripcion_Transferencia) {
		this.descripcion_Transferencia = descripcion_Transferencia;
	}

	public CommandButton getBtnGuardar_Transferencia() {
		return btnGuardar_Transferencia;
	}

	public void setBtnGuardar_Transferencia(CommandButton btnGuardar_Transferencia) {
		this.btnGuardar_Transferencia = btnGuardar_Transferencia;
	}

	public CommandButton getBtnLimpiar_Transferencia() {
		return btnLimpiar_Transferencia;
	}

	public void setBtnLimpiar_Transferencia(CommandButton btnLimpiar_Transferencia) {
		this.btnLimpiar_Transferencia = btnLimpiar_Transferencia;
	}

	public CommandButton getBtnLimpiar_consignacion() {
		return btnLimpiar_consignacion;
	}

	public void setBtnLimpiar_consignacion(CommandButton btnLimpiar_consignacion) {
		this.btnLimpiar_consignacion = btnLimpiar_consignacion;
	}

	public CommandButton getBtnLimpiar_Retiro() {
		return btnLimpiar_Retiro;
	}

	public void setBtnLimpiar_Retiro(CommandButton btnLimpiar_Retiro) {
		this.btnLimpiar_Retiro = btnLimpiar_Retiro;
	}

	public CommandButton getBtnGuardar_consignacion() {
		return btnGuardar_consignacion;
	}

	public void setBtnGuardar_consignacion(CommandButton btnGuardar_consignacion) {
		this.btnGuardar_consignacion = btnGuardar_consignacion;
	}

	public InputText getTxtNumeroCuenta_Retiro() {
		return txtNumeroCuenta_Retiro;
	}

	public void setTxtNumeroCuenta_Retiro(InputText txtNumeroCuenta_Retiro) {
		this.txtNumeroCuenta_Retiro = txtNumeroCuenta_Retiro;
	}

	public InputText getTxtCedulaUsuario_Retiro() {
		return txtCedulaUsuario_Retiro;
	}

	public void setTxtCedulaUsuario_Retiro(InputText txtCedulaUsuario_Retiro) {
		this.txtCedulaUsuario_Retiro = txtCedulaUsuario_Retiro;
	}

	public long getCedulaUsuario_Retiro() {
		return cedulaUsuario_Retiro;
	}

	public void setCedulaUsuario_Retiro(long cedulaUsuario_Retiro) {
		this.cedulaUsuario_Retiro = cedulaUsuario_Retiro;
	}

	public BigDecimal getValorRetiro() {
		return valorRetiro;
	}

	public void setValorRetiro(BigDecimal valorRetiro) {
		this.valorRetiro = valorRetiro;
	}

	public InputText getTxtValorRetiro() {
		return txtValorRetiro;
	}

	public void setTxtValorRetiro(InputText txtValorRetiro) {
		this.txtValorRetiro = txtValorRetiro;
	}

	public InputTextarea getDescripcion_Retiro() {
		return descripcion_Retiro;
	}

	public void setDescripcion_Retiro(InputTextarea descripcion_Retiro) {
		this.descripcion_Retiro = descripcion_Retiro;
	}

	public CommandButton getBtnGuardar_Retiro() {
		return btnGuardar_Retiro;
	}

	public void setBtnGuardar_Retiro(CommandButton btnGuardar_Retiro) {
		this.btnGuardar_Retiro = btnGuardar_Retiro;
	}

	public InputTextarea getDescripcion_consignacion() {
		return descripcion_consignacion;
	}

	public void setDescripcion_consignacion(InputTextarea descripcion_consignacion) {
		this.descripcion_consignacion = descripcion_consignacion;
	}

	public InputText getTxtValorConsigancion() {
		return txtValorConsigancion;
	}

	public void setTxtValorConsigancion(InputText txtValorConsigancion) {
		this.txtValorConsigancion = txtValorConsigancion;
	}

	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	public InputText getTxtNumeroCuenta_consignacion() {
		return txtNumeroCuenta_consignacion;
	}

	public void setTxtNumeroCuenta_consignacion(InputText txtNumeroCuenta_consignacion) {
		this.txtNumeroCuenta_consignacion = txtNumeroCuenta_consignacion;
	}

	public InputText getTxtCedulaUsuario_consignacion() {
		return txtCedulaUsuario_consignacion;
	}

	public void setTxtCedulaUsuario_consignacion(InputText txtCedulaUsuario_consignacion) {
		this.txtCedulaUsuario_consignacion = txtCedulaUsuario_consignacion;
	}

	public long getCedulaUsuario_consignacion() {
		return cedulaUsuario_consignacion;
	}

	public void setCedulaUsuario_consignacion(long cedulaUsuario_consignacion) {
		this.cedulaUsuario_consignacion = cedulaUsuario_consignacion;
	}

	public BigDecimal getValorConsignacion() {
		return valorConsignacion;
	}

	public void setValorConsignacion(BigDecimal valorConsignacion) {
		this.valorConsignacion = valorConsignacion;
	}
	
	public int getTanIndex() {
		return tanIndex;
	}

	public void setTanIndex(int tanIndex) {
		this.tanIndex = tanIndex;
	}
	

}
