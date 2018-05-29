package co.edu.icesi.PlataformaBanco.vista;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.password.Password;

import co.edu.icesi.PlataformaBanco.businessDelegate.IBusinessDelegate;
import co.edu.icesi.PlataformaBanco.modelo.Usuarios;

@ManagedBean
@ViewScoped
public class LoginView {

	@ManagedProperty(value = "#{businessDelegate}")
	private IBusinessDelegate businessDelegate;
	
	private long txtUsuario;
	private Password txtContrasenia;	
	
	public String action_ingresar() {
		try {
			Usuarios usuario = businessDelegate.findUsuarioById(txtUsuario);
			if(!usuario.getUsuClave().equals(txtContrasenia.getValue().toString()))
				throw new Exception("La contraseña es incorrecta");
			return "irGestionarCliente";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
		}
		return "";
	}

	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}
	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	public long getTxtUsuario() {
		return txtUsuario;
	}
	public void setTxtUsuario(long txtUsuario) {
		this.txtUsuario = txtUsuario;
	}
	public Password getTxtContrasenia() {
		return txtContrasenia;
	}
	public void setTxtContrasenia(Password txtContrasenia) {
		this.txtContrasenia = txtContrasenia;
	}


}
