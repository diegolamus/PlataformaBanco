package co.edu.icesi.PlataformaBanco.vista;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import co.edu.icesi.PlataformaBanco.businessDelegate.IBusinessDelegate;
import co.edu.icesi.PlataformaBanco.modelo.Clientes;
import co.edu.icesi.PlataformaBanco.modelo.Cuentas;

@ManagedBean
@ViewScoped
public class GestionarCuentasClienteView {

	@ManagedProperty(value = "#{businessDelegate}")
	private IBusinessDelegate businessDelegate;

	private InputText txtNumeroCuenta;
	private InputText txtClienteIDBind;
	private long txtClienteID;
	private InputText txtClave;
	private BigDecimal cueSaldo;
	private InputText txtCueSaldo;

	private Cuentas selectedCuenta;

	private CommandButton btnGuardar;
	private CommandButton btnModificar;
	private CommandButton btnCancelar;
	private CommandButton btnLimpiar;

	private List<Cuentas> lstCuentas;

	public String action_editar() {
		Cuentas entity = selectedCuenta;
		if (entity == null) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Por favor seleccione una cuenta", ""));
		} else {
			action_limpiar();
			txtClienteID = entity.getClientes().getCliId();
			txtClienteIDBind.setDisabled(true);
			txtNumeroCuenta.setValue(entity.getCueNumero());
			txtClave.setValue(entity.getCueClave());
			txtClave.setDisabled(false);
			cueSaldo = entity.getCueSaldo();
			txtCueSaldo.setDisabled(false);
			btnGuardar.setDisabled(true);
			btnCancelar.setDisabled(false);
			btnModificar.setDisabled(false);
			btnLimpiar.setDisabled(true);
		}
		return "";
	}

	public String action_inactivar() {
		Cuentas entity = selectedCuenta;
		if (entity == null) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Por favor seleccione una cuenta", ""));
		} else {
			try {
				// Inactivar cuenta
				businessDelegate.inactivarCuenta(entity);
				FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Se ha inactivado/activado el cliente exitosamente", ""));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
			}
		}
		return "";
	}

	public String action_guardar() {
		// Se crea la cuenta
		Cuentas cuenta = new Cuentas();
		try {
			// Se agrega el cliente a la cuenta
			Clientes cliente = businessDelegate.findClienteByID(txtClienteID);
			cuenta.setClientes(cliente);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}
		// Se pone la cuenta como activa
		cuenta.setCueActiva("S");
		cuenta.setCueNumero(txtNumeroCuenta.getValue().toString());
		cuenta.setCueClave(txtClave.getValue().toString());
		cuenta.setCueSaldo(cueSaldo);
		try {
			businessDelegate.crearCuenta(cuenta);
			action_limpiar();
			lstCuentas = null;
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha creado la cuenta exitosamente", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}
		return "";
	}

	public String action_modificar() {
		// Se crea la cuenta
		Cuentas cuenta = new Cuentas();
		try {
			// Se agrega el cliente a la cuenta
			Clientes cliente = businessDelegate.findClienteByID(txtClienteID);
			cuenta.setClientes(cliente);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}
		// Se actualiza la cuenta
		cuenta.setCueNumero(txtNumeroCuenta.getValue().toString());
		cuenta.setCueClave(txtClave.getValue().toString());
		cuenta.setCueSaldo(cueSaldo);
		cuenta.setCueActiva("S");
		try {
			businessDelegate.modificarCuenta(cuenta);
			action_cancelar();
			lstCuentas = null;
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado la cuenta exitosamente", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}
		return "";
	}

	public String action_cancelar() {
		btnModificar.setDisabled(true);
		btnLimpiar.setDisabled(false);
		action_limpiar();
		return "";
	}

	public String action_limpiar() {
		// Se acomodan los botones
		btnGuardar.setDisabled(true);
		btnCancelar.setDisabled(true);
		txtClienteIDBind.setDisabled(false);
		txtNumeroCuenta.setDisabled(true);
		txtClave.setDisabled(true);
		txtCueSaldo.setDisabled(true);
		// Se limpiean los campos
		txtNumeroCuenta.resetValue();
		txtClienteIDBind.resetValue();
		txtClienteID = 0;
		txtClave.resetValue();
		txtCueSaldo.resetValue();
		cueSaldo = null;
		return "";
	}

	public void txtClienteIdListener() {
		try {
			if (txtClienteID == 0) {
				throw new Exception("Debe ingresar un id valido");
			}
			// Se busca que el cliente exista
			Clientes cliente = businessDelegate.findClienteByID(txtClienteID);
			if (cliente == null) {
				throw new Exception("El cliente no existe, ingrese un numero de id valido para crear una cuenta");
			} else {
				// Se acomodan los bototnes para crear
				btnGuardar.setDisabled(false);
				txtClienteIDBind.setDisabled(true);
				txtNumeroCuenta.setDisabled(false);
				txtClave.setDisabled(false);
				txtCueSaldo.setDisabled(false);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}
	}

	public InputText getTxtNumeroCuenta() {
		return txtNumeroCuenta;
	}

	public void setTxtNumeroCuenta(InputText txtNumeroCuenta) {
		this.txtNumeroCuenta = txtNumeroCuenta;
	}

	public InputText getTxtClienteIDBind() {
		return txtClienteIDBind;
	}

	public void setTxtClienteIDBind(InputText txtClienteIDBind) {
		this.txtClienteIDBind = txtClienteIDBind;
	}

	public long getTxtClienteID() {
		return txtClienteID;
	}

	public void setTxtClienteID(long txtClienteID) {
		this.txtClienteID = txtClienteID;
	}

	public InputText getTxtClave() {
		return txtClave;
	}

	public void setTxtClave(InputText txtClave) {
		this.txtClave = txtClave;
	}

	public BigDecimal getCueSaldo() {
		return cueSaldo;
	}

	public void setCueSaldo(BigDecimal cueSaldo) {
		this.cueSaldo = cueSaldo;
	}

	public CommandButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(CommandButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public CommandButton getBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(CommandButton btnModificar) {
		this.btnModificar = btnModificar;
	}

	public CommandButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(CommandButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}

	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}

	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	public List<Cuentas> getLstCuentas() {
		if (lstCuentas == null) {
			try {
				lstCuentas = businessDelegate.findAllCuentas();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
			}
		}
		return lstCuentas;
	}

	public void setLstCuentas(List<Cuentas> lstCuentas) {
		this.lstCuentas = lstCuentas;
	}

	public InputText getTxtCueSaldo() {
		return txtCueSaldo;
	}

	public void setTxtCueSaldo(InputText txtCueSaldo) {
		this.txtCueSaldo = txtCueSaldo;
	}
	public Cuentas getSelectedCuenta() {
		return selectedCuenta;
	}

	public void setSelectedCuenta(Cuentas selectedCuenta) {
		this.selectedCuenta = selectedCuenta;
	}

}
