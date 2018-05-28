package co.edu.icesi.PlataformaBanco.vista;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputtext.InputText;
import co.edu.icesi.PlataformaBanco.businessDelegate.IBusinessDelegate;

import co.edu.icesi.PlataformaBanco.modelo.Consignaciones;
import co.edu.icesi.PlataformaBanco.modelo.Cuentas;
import co.edu.icesi.PlataformaBanco.modelo.Retiros;
import co.edu.icesi.PlataformaBanco.modelo.Transferencias;

@ManagedBean
@ViewScoped
public class ConsultasView {

	@ManagedProperty(value = "#{businessDelegate}")
	private IBusinessDelegate businessDelegate;

	private long cedulaCliente;
	private Date date_fechaInicio;
	private Date date_fechaFinal;
	private Calendar calendar_fechaInicio;
	private Calendar calendar_fechaFinal;
	private InputText txtNumeroCuenta;

	private List<Cuentas> cuentas;
	private List<Transferencias> transferencias;
	private List<Consignaciones> consignaciones;
	private List<Retiros> retiros;

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
		txtNumeroCuenta.setValue("");
		return "";
	}

	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	public long getCedulaUsuario() {
		return cedulaCliente;
	}

	public void setCedulaUsuario(long cedulaUsuario) {
		this.cedulaCliente = cedulaUsuario;
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

	public List<Cuentas> getCuentas() {
		//TODO revisar
		if(cuentas ==null) {
			try {
				cuentas = businessDelegate.findAllCuentas();
				if(cedulaCliente!=0) {
					for (Cuentas cuentas2 : cuentas) {
						if(!(cuentas2.getClientes().getCliId()==cedulaCliente))
							cuentas.remove(cuentas2);
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cuentas;
	}

	public void setCuentas(List<Cuentas> cuentas) {
		this.cuentas = cuentas;
	}

	public List<Transferencias> getTransferencias() {
		// TODO revisar
		if (transferencias == null) {
			try {
				transferencias = businessDelegate.findAllTransferencia();
				if (cedulaCliente != 0) {
					for (Transferencias transferencias2 : transferencias) {
						if (!(transferencias2.getCuentasByOrigenCueNumero().getClientes().getCliId() == cedulaCliente))
							transferencias.remove(transferencias2);
					}
				}
				if (date_fechaInicio != null) {
					for (Transferencias transferencias2 : transferencias) {
						if (transferencias2.getTranFecha().compareTo(date_fechaInicio) < 0)
							transferencias.remove(transferencias2);
					}
				}
				if (date_fechaFinal != null) {
					for (Transferencias transferencias2 : transferencias) {
						if (transferencias2.getTranFecha().compareTo(date_fechaFinal) > 0)
							transferencias.remove(transferencias2);
					}
				}
				if (!txtNumeroCuenta.getValue().toString().trim().equals("")) {
					for (Transferencias transferencias2 : transferencias) {
						if (!transferencias2.getCuentasByOrigenCueNumero().getCueNumero()
								.equals(txtNumeroCuenta.getValue().toString()))
							transferencias.remove(transferencias2);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return transferencias;
	}

	public void setTransferencias(List<Transferencias> transferencias) {
		this.transferencias = transferencias;
	}

	public List<Consignaciones> getConsignaciones() {
		// TODO revisar
		if (consignaciones == null) {
			try {
				consignaciones = businessDelegate.findAllConsiganciones();
				if (cedulaCliente != 0) {
					for (Consignaciones consignaciones2 : consignaciones) {
						if (!(consignaciones2.getCuentas().getClientes().getCliId() == cedulaCliente))
							consignaciones.remove(consignaciones2);
					}
				}
				if (date_fechaInicio != null) {
					for (Consignaciones consignaciones2 : consignaciones) {
						if (consignaciones2.getConFecha().compareTo(date_fechaInicio) < 0)
							consignaciones.remove(consignaciones2);
					}
				}
				if (date_fechaFinal != null) {
					for (Consignaciones consignaciones2 : consignaciones) {
						if (consignaciones2.getConFecha().compareTo(date_fechaFinal) > 0)
							consignaciones.remove(consignaciones2);
					}
				}
				if (!txtNumeroCuenta.getValue().toString().trim().equals("")) {
					for (Consignaciones consignaciones2 : consignaciones) {
						if (!consignaciones2.getCuentas().getCueNumero()
								.equals(txtNumeroCuenta.getValue().toString()))
							consignaciones.remove(consignaciones2);
					}
				}
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
		// TODO revisar
		if (retiros == null) {
			try {
				retiros = businessDelegate.findAllRetiros();
				if (cedulaCliente != 0) {
					for (Retiros retiros2 : retiros) {
						if (!(retiros2.getCuentas().getClientes().getCliId() == cedulaCliente))
							retiros.remove(retiros2);
					}
				}
				if (date_fechaInicio != null) {
					for (Retiros retiros2 : retiros) {
						if (retiros2.getRetFecha().compareTo(date_fechaInicio) < 0)
							retiros.remove(retiros2);
					}
				}
				if (date_fechaFinal != null) {
					for (Retiros retiros2 : retiros) {
						if (retiros2.getRetFecha().compareTo(date_fechaFinal) > 0)
							retiros.remove(retiros2);
					}
				}
				if (!txtNumeroCuenta.getValue().toString().trim().equals("")) {
					for (Retiros retiros2 : retiros) {
						if (!retiros2.getCuentas().getCueNumero()
								.equals(txtNumeroCuenta.getValue().toString()))
							retiros.remove(retiros2);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retiros;
	}

	public void setRetiros(List<Retiros> retiros) {
		this.retiros = retiros;
	}

}
