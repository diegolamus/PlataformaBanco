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
		// Se realizan las consultas necesarias
		try {
			//Se consultan las cuentas del cliente
			cuentas = businessDelegate.consultarCuentasPorCliente(cedulaCliente);
			// Se verifica si se ingreso cuenta
			boolean ingresoCuenta = (txtNumeroCuenta.getValue() != null
					&& !txtNumeroCuenta.getValue().toString().trim().equals(""));
			// Se verifica si ingreso fecha inicio
			boolean ingresoFechaInicio = (date_fechaInicio != null);
			// Se verifica si ingreso fecha de fin
			boolean ingresoFechaFin = (date_fechaFinal != null);
			// Se inicializan fechas que abarquen todas las transacciones
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date inicio = sdf.parse("21/12/1900");
			Date fin = new Date();
			// Se asigna fecha de inicio si se ingreso
			if (ingresoFechaInicio) {
				inicio = date_fechaInicio;
			}
			// Se asigna fecha de fin si se ingreso
			if (ingresoFechaFin) {
				fin = date_fechaFinal;
			}
			// Se verifican todos los casos para consultar
			if (!ingresoFechaInicio && !ingresoFechaFin && !ingresoCuenta) {
				retiros = businessDelegate.consultarRetirosPorCliente(cedulaCliente);
				transferencias = businessDelegate.consultarTransferenciasPorCliente(cedulaCliente);
				consignaciones = businessDelegate.consultarConsignacionesPorCliente(cedulaCliente);
			} else if (!ingresoCuenta && (ingresoFechaFin || ingresoFechaInicio)) {
				retiros = businessDelegate.consultarRetirosPorClientePorRangoFechas(cedulaCliente, inicio, fin);
				transferencias = businessDelegate.consultarTransferenciasPorClientePorRangoFechas(cedulaCliente, inicio,fin);
				consignaciones = businessDelegate.consultarConsignacionesPorClientePorRangoFechas(cedulaCliente,inicio, fin);
			} else if (ingresoCuenta) {
				Cuentas cuenta = businessDelegate.findCuentaByID(txtNumeroCuenta.getValue().toString().trim());
				retiros = businessDelegate.consultarRetirosPorClientePorRangoFechasPorCuenta(cedulaCliente, inicio, fin,cuenta);
				transferencias = businessDelegate.consultarTransferenciasPorClientePorRangoFechasPorCuenta(cedulaCliente, inicio, fin, cuenta);
				consignaciones = businessDelegate.consultarConsignacionesPorClientePorRangoFechasPorCuenta(cedulaCliente, inicio, fin, cuenta);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}
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
		return cuentas;
	}

	public void setCuentas(List<Cuentas> cuentas) {
		this.cuentas = cuentas;
	}

	public List<Transferencias> getTransferencias() {
		return transferencias;
	}

	public void setTransferencias(List<Transferencias> transferencias) {
		this.transferencias = transferencias;
	}

	public List<Consignaciones> getConsignaciones() {
		return consignaciones;
	}

	public void setConsignaciones(List<Consignaciones> consignaciones) {
		this.consignaciones = consignaciones;
	}

	public List<Retiros> getRetiros() {
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
