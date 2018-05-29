package co.edu.icesi.PlataformaBanco.vista;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputmask.InputMask;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;

import co.edu.icesi.PlataformaBanco.businessDelegate.IBusinessDelegate;
import co.edu.icesi.PlataformaBanco.modelo.Clientes;
import co.edu.icesi.PlataformaBanco.modelo.TiposDocumentos;

@ManagedBean
@ViewScoped
public class GestionarClienteView {

	@ManagedProperty(value = "#{businessDelegate}")
	private IBusinessDelegate businessDelegate;

	private InputText txtIdentificacion2;
	private long txtIdentificacion;
	private long valueTipoIdentificacion;
	private InputText txtNombre;
	private InputText txtDireccion;
	private InputMask txtTelefono;
	private String telefono;
	private InputText txtMail;
	private SelectOneMenu menutipodoc;

	private CommandButton btnGuardar;
	private CommandButton btnModificar;
	private CommandButton btnCancelar;
	private CommandButton btnLimpiar;

	private Clientes selectedCliente;

	private List<Clientes> lstClientes;
	private List<SelectItem> lstTiposDoc;

	public void listener_buscarCliente() {
		try {
			@SuppressWarnings("unused")
			Clientes cliente = businessDelegate.findClienteByID(txtIdentificacion);
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El cliente ya existe", ""));
		} catch (Exception e) {
			// Si el cliente no existe se retorna una excepcion
			txtIdentificacion2.setDisabled(true);
			txtNombre.setDisabled(false);
			txtDireccion.setDisabled(false);
			txtTelefono.setDisabled(false);
			txtMail.setDisabled(false);
			btnGuardar.setDisabled(false);
			menutipodoc.setDisabled(false);
		}

	}

	public String action_editar() {
		Clientes cliente = selectedCliente;
		if (cliente == null)
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Por favor seleccione un cliente", ""));
		else {
			action_limpiar();
			// Se acomodan los botones
			btnGuardar.setDisabled(true);
			btnLimpiar.setDisabled(true);
			btnModificar.setDisabled(false);
			btnCancelar.setDisabled(false);
			// Se acomoda el campo de id
			txtIdentificacion = cliente.getCliId();
			txtIdentificacion2.setDisabled(true);
			// Se acomodan los campos
			valueTipoIdentificacion = cliente.getTiposDocumentos().getTdocCodigo();
			txtNombre.setValue(cliente.getCliNombre());
			txtDireccion.setValue(cliente.getCliDireccion());
			setTelefono(cliente.getCliTelefono());
			txtMail.setValue(cliente.getCliMail());
			txtIdentificacion2.setDisabled(true);
			txtNombre.setDisabled(false);
			txtDireccion.setDisabled(false);
			txtTelefono.setDisabled(false);
			txtMail.setDisabled(false);
			menutipodoc.setDisabled(false);
		}
		return "";
	}

	public String action_inactivar() {
		Clientes cliente = selectedCliente;
		if (cliente == null)
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Por favor seleccione un cliente", ""));
		else {
			try {
				businessDelegate.inactivarCliente(cliente);
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
		// Se crea el cliente
		Clientes cli = new Clientes();
		// Se recuperan los datos de los clientes
		cli.setCliId(txtIdentificacion);
		try {
			cli.setTiposDocumentos(businessDelegate.finTiposDocumentosByID(valueTipoIdentificacion));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}
		cli.setCliNombre(txtNombre.getValue().toString());
		cli.setCliDireccion(txtDireccion.getValue().toString());
		cli.setCliTelefono(txtTelefono.getValue().toString());
		cli.setCliMail(txtMail.getValue().toString());
		cli.setCliActiva("S");
		try {
			// Se guarda el cliente
			businessDelegate.crearCliente(cli);
			// La lista clientes se pone en nulo para que se vuelva a cargar la tabla
			lstClientes = null;
			// Se arroja mensaje de exito
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha guardado el cliente exitosamente", ""));
			// Se limpian los campos
			action_limpiar();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}
		return "";
	}

	public String action_modificar() {
		// Se crea el cliente
		Clientes cli = new Clientes();
		// Se recuperan los datos de los clientes
		cli.setCliId(txtIdentificacion);
		try {
			cli.setTiposDocumentos(businessDelegate.finTiposDocumentosByID(valueTipoIdentificacion));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}
		cli.setCliNombre(txtNombre.getValue().toString());
		cli.setCliDireccion(txtDireccion.getValue().toString());
		cli.setCliTelefono(txtTelefono.getValue().toString());
		cli.setCliMail(txtMail.getValue().toString());
		cli.setCliActiva("S");
		try {
			// Se actualiza el cliente
			businessDelegate.modificarCliente(cli);
			// La lista clientes se pone en nulo para que se vuelva a cargar la tabla
			lstClientes = null;
			// Se arroja mensaje de exito
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el cliente exitosamente", ""));
			// Se limpian los campos
			action_limpiar();
			// Se acomodan los botones
			btnGuardar.setDisabled(false);
			btnLimpiar.setDisabled(false);
			btnModificar.setDisabled(true);
			btnCancelar.setDisabled(true);
			// Se habilita el texto de id
			txtIdentificacion2.setDisabled(false);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}
		return "";
	}

	public String action_cancelar() {
		// Se acomodan los botones
		btnGuardar.setDisabled(false);
		btnLimpiar.setDisabled(false);
		btnModificar.setDisabled(true);
		btnCancelar.setDisabled(true);
		// Se limpian los campos
		action_limpiar();
		// Se habilita el texto de id
		txtIdentificacion2.setDisabled(false);
		return "";
	}

	public String action_limpiar() {
		// Se limpian los datos
		txtIdentificacion = 0;
		valueTipoIdentificacion = -1;
		txtNombre.resetValue();
		txtDireccion.resetValue();
		telefono = "";
		txtMail.resetValue();
		txtNombre.setDisabled(true);
		txtDireccion.setDisabled(true);
		txtTelefono.setDisabled(true);
		txtMail.setDisabled(true);
		btnGuardar.setDisabled(true);
		txtIdentificacion2.setDisabled(false);
		menutipodoc.setDisabled(true);
		return "";
	}

	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	public long getTxtIdentificacion() {
		return txtIdentificacion;
	}

	public void setTxtIdentificacion(long txtIdentificacion) {
		this.txtIdentificacion = txtIdentificacion;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public InputText getTxtDireccion() {
		return txtDireccion;
	}

	public void setTxtDireccion(InputText txtDireccion) {
		this.txtDireccion = txtDireccion;
	}

	public InputMask getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(InputMask txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public InputText getTxtMail() {
		return txtMail;
	}

	public void setTxtMail(InputText txtMail) {
		this.txtMail = txtMail;
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
	public SelectOneMenu getTipodoc() {
		return menutipodoc;
	}

	public void setTipodoc(SelectOneMenu menutipodoc) {
		this.menutipodoc = menutipodoc;
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

	public List<Clientes> getLstClientes() {
		if (lstClientes == null)
			try {
				lstClientes = businessDelegate.findAllCliente();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return lstClientes;
	}

	public void setLstClientes(List<Clientes> lstClientes) {
		this.lstClientes = lstClientes;
	}

	public List<SelectItem> getLstTiposDoc() {
		if (lstTiposDoc == null) {
			lstTiposDoc = new ArrayList<>();
			try {
				List<TiposDocumentos> tdocs = businessDelegate.finAllTiposDocumentos();
				for (TiposDocumentos tiposDocumentos : tdocs) {
					lstTiposDoc.add(new SelectItem(tiposDocumentos.getTdocCodigo(), tiposDocumentos.getTdocNombre()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lstTiposDoc;
	}

	public void setLstTiposDoc(List<SelectItem> lstTiposDoc) {
		this.lstTiposDoc = lstTiposDoc;
	}

	public long getValueTipoIdentificacion() {
		return valueTipoIdentificacion;
	}

	public void setValueTipoIdentificacion(long valueTipoIdentificacion) {
		this.valueTipoIdentificacion = valueTipoIdentificacion;
	}

	public InputText getTxtIdentificacion2() {
		return txtIdentificacion2;
	}

	public void setTxtIdentificacion2(InputText txtIdentificacion2) {
		this.txtIdentificacion2 = txtIdentificacion2;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Clientes getSelectedCliente() {
		return selectedCliente;
	}

	public void setSelectedCliente(Clientes selectedCliente) {
		this.selectedCliente = selectedCliente;
	}

	public SelectOneMenu getMenutipodoc() {
		return menutipodoc;
	}

	public void setMenutipodoc(SelectOneMenu menutipodoc) {
		this.menutipodoc = menutipodoc;
	}
	

}
