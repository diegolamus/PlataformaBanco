package co.edu.icesi.PlataformaBanco.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.PlataformaBanco.dao.IClientesDAO;
import co.edu.icesi.PlataformaBanco.dao.ITipoDocumentoDAO;
import co.edu.icesi.PlataformaBanco.modelo.Clientes;
import co.edu.icesi.PlataformaBanco.modelo.TiposDocumentos;

@Service
@Scope("singleton")
public class ClienteLogic implements IClienteLogic {

	@Autowired
	private IClientesDAO clienteDAO;
	@Autowired
	private ITipoDocumentoDAO topiDocumentoDAO;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public void save(Clientes entity) throws Exception {
		//validamos que se ingrese el cliente
		if(entity == null) 
			throw new Exception("Debe ingresar un cliente");
		//validamos que el id del cliente no sea null
		if(entity.getCliId() <= 0) 
			throw new Exception("Debe ingresar el id del cliente");
		//validamos que el nombre del cliente no sea nulo y que no esta vacio
		if(entity.getCliNombre() == null || entity.getCliNombre().trim().equals("")) 
			throw new Exception("debe ingresar el nombre");
		//validamos que la direccian del cliente no sea nula y no esta vacia
		if(entity.getCliDireccion() == null ||entity.getCliDireccion().trim().equals("") ) 
			throw new Exception("Debe ingresar una direccion");
		//validamos que el telefono del cliente no sea nulo y no esta vacio
		if(entity.getCliTelefono() == null || entity.getCliTelefono().trim().equals("")) 
			throw new Exception("Debe ingresar un numero de telefono");
		//validamos que el email del cliente no se nulo, no este vacio y contenga una @
		if(entity.getCliMail() == null || entity.getCliMail().trim().equals("") || !entity.getCliMail().contains("@"))
			throw new Exception("Debe ingresar un correo valido");
		//validamos qie el email del cliente tenga menos de 50 caracteres
		if(entity.getCliMail() == null || entity.getCliMail().length()>50) 
			throw new Exception("El email debe tener menos de 50 caracteres");
		//validamos el cliente que se va a agregar se agrugue como activo
		if(entity.getCliActiva() == null || !entity.getCliActiva().equals("S")) 
			throw new Exception("El cliente debe registrarse como activo");
		//validamos que el tipo de documento no sea nulo
		if(entity.getTiposDocumentos() == null || entity.getTiposDocumentos().getTdocCodigo() == 0)
			throw new Exception("Debe ingresar un tipo de documento");
		//validamos que el tipo de documento existe
		TiposDocumentos tipoDoc = topiDocumentoDAO.findById(entity.getTiposDocumentos().getTdocCodigo());
		if(tipoDoc == null) 
			throw new Exception("El tipo de documento: " + entity.getTiposDocumentos().getTdocCodigo() + " no existe");
		//validamos que el cliente no exista
		Clientes cliente = clienteDAO.findById(entity.getCliId());
		if(cliente != null)
			throw new Exception("El cliente: " + entity.getCliId() + " ya existe");
		//guardamos el cliente
		clienteDAO.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public void update(Clientes entity) throws Exception {
		//validamos que se ingrese el cliente
		if(entity == null) 
			throw new Exception("Debe ingresar un cliente");
		//validamos que el id del cliente no sea null
		if(entity.getCliId() == 0) 
			throw new Exception("Debe ingresar el id del cliente");
		//validamos que el nuevo nombre del cliente no sea nulo y que no este vacio
		if(entity.getCliNombre() == null || entity.getCliNombre().trim().equals(""))
			throw new Exception("debe ingresar el nombre");
		//validamos que la nueva direccion del cliente no sea nula y no este vacia
		if(entity.getCliDireccion() == null ||entity.getCliDireccion().trim().equals("")) 
			throw new Exception("Debe ingresar una direccion");
		//validamos que el nuevo telefono del cliente no sea nulo y no este vacio
		if(entity.getCliTelefono() == null || entity.getCliTelefono().trim().equals("")) 
			throw new Exception("Debe ingresar un numero de telefono");
		//validamos que el nuevo email del cliente no se nulo, no este vacio y contenga una @
		if(entity.getCliMail() == null || entity.getCliMail().trim().equals("") || !entity.getCliMail().contains("@"))
			throw new Exception("Debe ingresar un correo valido");
		//validamos el cliente se encuentre activo
		if(entity.getCliActiva() == null || !entity.getCliActiva().equals("S")) 
			throw new Exception("El cliente debe estar activo para actualizarlo");
		//validamos que el nuevo email del cliente tenga menos de 50 caracteres
		if(entity.getCliMail() == null || entity.getCliMail().length()>50) 
			throw new Exception("El email debe tener menos de 50 caracteres");
		//validamos que el tipo de documento no sea nulo
		if(entity.getTiposDocumentos() == null || entity.getTiposDocumentos().getTdocCodigo() == 0) 
			throw new Exception("Debe ingresar un tipo de documento");
		//validamos que el tipo de documento existe
		TiposDocumentos tipoDoc = topiDocumentoDAO.findById(entity.getTiposDocumentos().getTdocCodigo());
		if(tipoDoc == null) 
			throw new Exception("El tipo de documento: " + entity.getTiposDocumentos().getTdocCodigo() + " no existe");
		//validamos que el cliente  exista
		Clientes cliente = clienteDAO.findById(entity.getCliId());
		if(cliente == null) 
			throw new Exception("El cliente: " + entity.getCliId() + " no existe y no se puede actualizar");
		//actualizamos el cliente
		clienteDAO.update(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public void invalidate(Clientes entity) throws Exception {
		//validamos que se ingrese el cliente
		if(entity == null) 
			throw new Exception("Debe ingresar un cliente");
		//validamos que el id del cliente no sea null y que se valido
		if(entity.getCliId() <= 0) 
			throw new Exception("Debe ingresar el id del cliente");
		//validamos que el cliente  exista
		Clientes cliente = clienteDAO.findById(entity.getCliId());
		if(cliente == null) 
			throw new Exception("El cliente: " + entity.getCliId() + " no existe y no se puede eliminar");
		//invalidamos o validamos el cliente
		if(entity.getCliActiva().trim().equals("S")) {
			entity.setCliActiva("N");
		}
		else {
			entity.setCliActiva("S");
		}
		clienteDAO.update(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Clientes findById(Long id) throws Exception {
		//Validamos que el id no sea nulo y que se mayor a cero
		if(id <=0) 
			throw new Exception("Debe ingresar una id valida");
		//validamos que el cliente  exista
		Clientes cliente = clienteDAO.findById(id);
		if(cliente == null) 
			throw new Exception("El cliente: " + id + " no existe");
		return cliente;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Clientes> findAll() throws Exception {
		return clienteDAO.findAll();
	}

}
