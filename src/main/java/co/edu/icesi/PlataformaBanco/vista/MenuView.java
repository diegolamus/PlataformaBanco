package co.edu.icesi.PlataformaBanco.vista;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class MenuView {

	private String usuario;
	
	@PostConstruct
	public void setUsuario() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		usuario = "Cédula usuario: " + (String)session.getAttribute("usuario");
	}
	
	public String action_cerrasSesion() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", null);
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login.xhtml";
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}
