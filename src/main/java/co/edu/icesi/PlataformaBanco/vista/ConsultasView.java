package co.edu.icesi.PlataformaBanco.vista;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.PlataformaBanco.businessDelegate.IBusinessDelegate;
import co.edu.icesi.PlataformaBanco.modelo.Clientes;
import co.edu.icesi.PlataformaBanco.modelo.Consignaciones;
import co.edu.icesi.PlataformaBanco.modelo.Cuentas;
import co.edu.icesi.PlataformaBanco.modelo.Retiros;
import co.edu.icesi.PlataformaBanco.modelo.Transferencias;

@ManagedBean
@ViewScoped
public class ConsultasView {

	@ManagedProperty(value = "#{businessDelegate}")
	private IBusinessDelegate businessDelegate;

	private InputText txtCedulaCliente;
	private long cedulaCliente;
	private Date date_fechaInicio;
	private Date date_fechaFinal;
	private Calendar calendar_fechaInicio;
	private Calendar calendar_fechaFinal;
	private InputText txtNumeroCuenta;
	private CommandButton btnBuscar;

	private List<Cuentas> cuentas;
	private List<Transferencias> transferencias;
	private List<Consignaciones> consignaciones;
	private List<Retiros> retiros;
	
	public void listener_buscarCliente() {
		try {
			@SuppressWarnings("unused")
			Clientes cliente = businessDelegate.findClienteByID(cedulaCliente);
			calendar_fechaInicio.setDisabled(false);
			calendar_fechaFinal.setDisabled(false);
			txtNumeroCuenta.setDisabled(false);
			btnBuscar.setDisabled(false);
			txtCedulaCliente.setDisabled(true);
		} catch (Exception e) {
			// Si el cliente no existe se retorna una excepcion
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}

	} 
	public String action_buscar() {
		// Se ponen listas en null para que se ejecuten los get y se hagan los filtros
		// correspondientes
		cuentas = null;
		transferencias = null;
		consignaciones = null;
		retiros = null;
		return "";
	}

	public String action_limpiar() {
		// Se limpian los campos de filtros
		cedulaCliente = 0;
		calendar_fechaInicio.setValue(null);
		calendar_fechaFinal.setValue(null);
		txtNumeroCuenta.setValue(null);
		cuentas = null;
		transferencias = null;
		consignaciones = null;
		retiros = null;
		calendar_fechaInicio.setDisabled(true);
		calendar_fechaFinal.setDisabled(true);
		txtNumeroCuenta.setDisabled(true);
		btnBuscar.setDisabled(true);
		txtCedulaCliente.setDisabled(false);
		return "";
	}

	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	public Date getDate_fechaInicio() {
		return date_fechaInicio;
	}

	public void setDate_fechaInicio(Date date_fechaInicio) {
		this.date_fechaInicio = date_fechaInicio;
	}

	public Date getDate_fechaFinal() {
		return date_fechaFinal;
	}

	public void setDate_fechaFinal(Date date_fechaFinal) {
		this.date_fechaFinal = date_fechaFinal;
	}

	public Calendar getCalendar_fechaInicio() {
		return calendar_fechaInicio;
	}

	public void setCalendar_fechaInicio(Calendar calendar_fechaInicio) {
		this.calendar_fechaInicio = calendar_fechaInicio;
	}

	public Calendar getCalendar_fechaFinal() {
		return calendar_fechaFinal;
	}

	public void setCalendar_fechaFinal(Calendar calendar_fechaFinal) {
		this.calendar_fechaFinal = calendar_fechaFinal;
	}

	public InputText getTxtNumeroCuenta() {
		return txtNumeroCuenta;
	}

	public void setTxtNumeroCuenta(InputText txtNumeroCuenta) {
		this.txtNumeroCuenta = txtNumeroCuenta;
	}

	@Transactional(readOnly = true)
	public List<Cuentas> getCuentas() {
		if (cuentas == null) {
			try {
				if (cedulaCliente != 0) {
					cuentas = businessDelegate.consultarCuentasPorCliente(cedulaCliente);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cuentas;
	}

	public void setCuentas(List<Cuentas> cuentas) {
		this.cuentas = cuentas;
	}

	public List<Transferencias> getTransferencias() {
		if (transferencias == null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date inicio = sdf.parse("21/12/1900");
				Date fin = new Date();
				if (date_fechaInicio != null) {
					inicio = date_fechaInicio;
				}
				if (date_fechaFinal != null) {
					fin = date_fechaFinal;
				}
				if(date_fechaInicio ==null&& date_fechaFinal==null)
					transferencias = businessDelegate.consultarTransferenciasPorCliente(cedulaCliente);
				else if(txtNumeroCuenta.getValue() == null || txtNumeroCuenta.getValue().toString().trim().equals(""))
					transferencias = businessDelegate.consultarTransferenciasPorClientePorRangoFechas(cedulaCliente, inicio, fin);
				else if(!txtNumeroCuenta.getValue().toString().trim().equals("")){
					Cuentas cuenta = businessDelegate.findCuentaByID(txtNumeroCuenta.getValue().toString().trim());
					transferencias = businessDelegate.consultarTransferenciasPorClientePorRangoFechasPorCuenta(cedulaCliente, inicio, fin, cuenta);
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
			}
		}
		return transferencias;
	}

	public void setTransferencias(List<Transferencias> transferencias) {
		this.transferencias = transferencias;
	}

	public List<Consignaciones> getConsignaciones() {
		if (consignaciones == null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date inicio = sdf.parse("21/12/1900");
				Date fin = new Date();
				if (date_fechaInicio != null) {
					inicio = date_fechaInicio;
				}
				if (date_fechaFinal != null) {
					fin = date_fechaFinal;
				}
				if(date_fechaInicio ==null&& date_fechaFinal==null)
					consignaciones = businessDelegate.consultarConsignacionesPorCliente(cedulaCliente);
				else if(txtNumeroCuenta.getValue() == null || txtNumeroCuenta.getValue().toString().trim().equals(""))
					consignaciones = businessDelegate.consultarConsignacionesPorClientePorRangoFechas(cedulaCliente, inicio, fin);
				else if(!txtNumeroCuenta.getValue().toString().trim().equals("")){
					Cuentas cuenta = businessDelegate.findCuentaByID(txtNumeroCuenta.getValue().toString().trim());
					consignaciones = businessDelegate.consultarConsignacionesPorClientePorRangoFechasPorCuenta(cedulaCliente, inicio, fin, cuenta);
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
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
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date inicio = sdf.parse("21/12/1900");
				Date fin = new Date();
				if (date_fechaInicio != null) {
					inicio = date_fechaInicio;
				}
				if (date_fechaFinal != null) {
					fin = date_fechaFinal;
				}
				if(date_fechaInicio ==null&& date_fechaFinal==null)
					retiros = businessDelegate.consultarRetirosPorCliente(cedulaCliente);
				else if(txtNumeroCuenta.getValue() == null || txtNumeroCuenta.getValue().toString().trim().equals(""))
					retiros = businessDelegate.consultarRetirosPorClientePorRangoFechas(cedulaCliente, inicio, fin);
				else if(!txtNumeroCuenta.getValue().toString().trim().equals("")){
					Cuentas cuenta = businessDelegate.findCuentaByID(txtNumeroCuenta.getValue().toString().trim());
					retiros = businessDelegate.consultarRetirosPorClientePorRangoFechasPorCuenta(cedulaCliente, inicio, fin, cuenta);
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
			}
		}
		return retiros;
	}

	public void setRetiros(List<Retiros> retiros) {
		this.retiros = retiros;
	}
	public long getCedulaCliente() {
		return cedulaCliente;
	}
	public void setCedulaCliente(long cedulaCliente) {
		this.cedulaCliente = cedulaCliente;
	}
	public CommandButton getBtnBuscar() {
		return btnBuscar;
	}
	public void setBtnBuscar(CommandButton btnBuscar) {
		this.btnBuscar = btnBuscar;
	}
	public InputText getTxtCedulaCliente() {
		return txtCedulaCliente;
	}
	public void setTxtCedulaCliente(InputText txtCedulaCliente) {
		this.txtCedulaCliente = txtCedulaCliente;
	}
	
	
	

}
