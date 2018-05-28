package co.edu.icesi.PlataformaBanco.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import co.edu.icesi.PlataformaBanco.dao.ITiposUsuariosDAO;
import co.edu.icesi.PlataformaBanco.modelo.TiposUsuarios;

@Service
@Scope("singleton")
public class TiposUsuariosLogic implements ITiposUsuariosLogic {

	@Autowired
	private ITiposUsuariosDAO tiposUsuariosDAO;
	
	@Override
	public void save(TiposUsuarios entity) throws Exception {
		//Verificar que el tipo de usuario  sea valido
		if(entity == null)
			throw new Exception("Se debe ingresar un tipo de usuario");
		//validar que el id del el tipo de usuario sea valido
		if(entity.getTusuCodigo() == 0)
			throw new Exception("Debe ingresar un id para el tipo de usuario valida");
		//Validamos que el nombre de usuario no sea nulo o vacio
		if(entity.getTusuNombre()==null || entity.getTusuNombre().trim().equals(""))
			throw new Exception("Debe ingresar un nombre para el tipo de usuario");
		//Validamos que el tipo de usuario no exista
		TiposUsuarios usuario = tiposUsuariosDAO.findById(entity.getTusuCodigo());
		if(usuario!=null)
			throw new Exception("El tipo de usuario ya existe");
		tiposUsuariosDAO.save(entity);
	}

	@Override
	public void update(TiposUsuarios entity) throws Exception {
		//Verificar que el tipo de usuario  sea valido
		if(entity == null)
			throw new Exception("Se debe ingresar un tipo de usuario");
		//validar que el id del el tipo de usuario sea valido
		if(entity.getTusuCodigo() == 0)
			throw new Exception("Debe ingresar un id para el tipo de usuario valida");
		//Validamos que el nombre de usuario no sea nulo o vacio
		if(entity.getTusuNombre()==null || entity.getTusuNombre().trim().equals(""))
			throw new Exception("Debe ingresar un nombre para el tipo de usuario");
		//Validamos que el tipo de usuario exista
		TiposUsuarios usuario = tiposUsuariosDAO.findById(entity.getTusuCodigo());
		if(usuario==null)
			throw new Exception("El tipo de usuario no existe");
		tiposUsuariosDAO.update(entity);
	}

	@Override
	public TiposUsuarios findById(Long id) throws Exception {
		//Validamos que el tipo de usuario exista
		TiposUsuarios usuario = tiposUsuariosDAO.findById(id);
		if(usuario==null)
			throw new Exception("El tipo de usuario no existe");
		return usuario;
	}

	@Override
	public List<TiposUsuarios> findAll() throws Exception {
		return tiposUsuariosDAO.findAll();
	}

}
