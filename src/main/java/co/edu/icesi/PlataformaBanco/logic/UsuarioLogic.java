package co.edu.icesi.PlataformaBanco.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import co.edu.icesi.PlataformaBanco.dao.ITiposUsuariosDAO;
import co.edu.icesi.PlataformaBanco.dao.IUsuariosDAO;
import co.edu.icesi.PlataformaBanco.modelo.TiposUsuarios;
import co.edu.icesi.PlataformaBanco.modelo.Usuarios;

@Service
@Scope("singleton")
public class UsuarioLogic implements IUsuarioLogic {

	@Autowired
	private ITiposUsuariosDAO tiposUsuarioDAO;
	
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Override
	public void save(Usuarios entity) throws Exception {
		//Validar que la entidad no sea nula
		if(entity==null)
			throw new Exception("Debe ingresar un usuario");
		//Validar que la cedula del usuario no sea nula
		if(entity.getUsuCedula() == 0)
			throw new Exception("Debe ingresar un numero de cedula");
		//Validar que el tipo de usuario no sea nulo
		if(entity.getTiposUsuarios() == null)
			throw new Exception("Debe ingresar un tipo de usuario valido");
		//Validas que el tipo de usuario exista
		TiposUsuarios usutip = tiposUsuarioDAO.findById(entity.getTiposUsuarios().getTusuCodigo());
		if(usutip == null)
			throw new Exception("El tipo de usuario no existe");
		//Validamos que el nombre no sea nulo ni vacio
		if(entity.getUsuNombre() == null || entity.getUsuNombre().trim().equals(""))
			throw new Exception("Debe ingresar un nombre válido");
		//Validamos que el usuario de log in no sea nulo ni vacio
		if(entity.getUsuLogin() == null || entity.getUsuLogin().trim().equals(""))
			throw new Exception("Debe ingresar un usuario de log in");
		//Validamos que la contraseña no sea nula ni vacia
		if(entity.getUsuClave() == null || entity.getUsuClave().trim().equals(""))
			throw new Exception("Debe ingresar una clave");
		usuariosDAO.save(entity);
	}

	@Override
	public void update(Usuarios entity) throws Exception {
		// Validar que la entidad no sea nula
		if (entity == null)
			throw new Exception("Debe ingresar un usuario");
		// Validar que la cedula del usuario no sea nula
		if (entity.getUsuCedula() == 0)
			throw new Exception("Debe ingresar un numero de cedula");
		// Validamos que el usuario exista
		Usuarios usu = usuariosDAO.findById(entity.getUsuCedula());
		if(usu==null)
			throw new Exception("El usuario no existe y no se puede actualizar");
		// Validar que el tipo de usuario no sea nulo
		if (entity.getTiposUsuarios() == null)
			throw new Exception("Debe ingresar un tipo de usuario valido");
		// Validas que el tipo de usuario exista
		TiposUsuarios usutip = tiposUsuarioDAO.findById(entity.getTiposUsuarios().getTusuCodigo());
		if (usutip == null)
			throw new Exception("El tipo de usuario no existe");
		// Validamos que el nombre no sea nulo ni vacio
		if (entity.getUsuNombre() == null || entity.getUsuNombre().trim().equals(""))
			throw new Exception("Debe ingresar un nombre válido");
		// Validamos que el usuario de log in no sea nulo ni vacio
		if (entity.getUsuLogin() == null || entity.getUsuLogin().trim().equals(""))
			throw new Exception("Debe ingresar un usuario de log in");
		// Validamos que la contraseña no sea nula ni vacia
		if (entity.getUsuClave() == null || entity.getUsuClave().trim().equals(""))
			throw new Exception("Debe ingresar una clave");
		usuariosDAO.update(entity);
		
	}

	@Override
	public Usuarios findById(long id) throws Exception {
		//Validamos que el id no sea nula
		if(id == 0)
			throw new Exception("Debe ingresar una cedula valida");
		//Validamos que el usuario exista
		Usuarios usu = usuariosDAO.findById(id);
		if(usu==null)
			throw new Exception("El usuario no existe");
		return usu;
	}

	@Override
	public List<Usuarios> findAll() throws Exception  {
		return usuariosDAO.findAll();
	}

	@Override
	public void delete(Usuarios entity) throws Exception  {
		// Validamos que el usuario no sea nulo
		if (entity == null)
			throw new Exception("Debe ingresar un usuario");
		//Validamos que el usuario exista
		Usuarios usu = usuariosDAO.findById(entity.getUsuCedula());
		if(usu==null)
			throw new Exception("El usuario no existe");
		//Veliminamos el usuario
		usuariosDAO.delete(entity);
	}

}
