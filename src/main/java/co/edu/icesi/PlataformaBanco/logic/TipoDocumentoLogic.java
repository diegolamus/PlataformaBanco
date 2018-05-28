package co.edu.icesi.PlataformaBanco.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import co.edu.icesi.PlataformaBanco.dao.ITipoDocumentoDAO;
import co.edu.icesi.PlataformaBanco.modelo.TiposDocumentos;

@Service
@Scope("singleton")
public class TipoDocumentoLogic implements ITipoDocumentoLogic{

	@Autowired
	private ITipoDocumentoDAO tipoDocumentoDAO;
	
	@Override
	public void save(TiposDocumentos entity) throws Exception {
		//Validamos que el tipo de documento no sea nulo
		if(entity==null)
			throw new Exception("Debe ingresar un tipo de documento");
		//Validamos que el c�digo del tipo de documento no sea nulo ni negativo
		if(entity.getTdocCodigo() <=0)
			throw new Exception("Debe ingresar un codigo valido para el tipo de documento");
		//Verificamos que el nombre del tipo de documento no sea nulo ni vacia
		if(entity.getTdocNombre() == null || entity.getTdocNombre().trim().equals(""))
			throw new Exception("Debe ingresar un nombre para el tipo de documento");
		//Validamos que el tipo de documento no exista
		TiposDocumentos tipo = tipoDocumentoDAO.findById(entity.getTdocCodigo());
		if(tipo!= null)
			throw new Exception("El tipo de documento ya existe");
		//Guardamos el tipo de documento
		tipoDocumentoDAO.save(entity);
	}

	@Override
	public void update(TiposDocumentos entity) throws Exception {
		//Validamos que el tipo de documento no sea nulo
		if(entity==null)
			throw new Exception("Debe ingresar un tipo de documento");
		//Validamos que el c�digo del tipo de documento no sea nulo ni negativo
		if(entity.getTdocCodigo() <=0)
			throw new Exception("Debe ingresar un codigo valido para el tipo de documento");
		//Verificamos que el nuevo nombre del tipo de documento no sea nulo ni vacia
		if(entity.getTdocNombre() == null || entity.getTdocNombre().trim().equals(""))
			throw new Exception("Debe ingresar un nombre para el tipo de documento");
		//Validamos que el tipo de documento exista
		TiposDocumentos tipo = tipoDocumentoDAO.findById(entity.getTdocCodigo());
		if(tipo == null)
			throw new Exception("El tipo de documento no existe y no se puede modificar");
		//actualizamos el tipo de documento
		tipoDocumentoDAO.update(entity);
	}

	@Override
	public void delete(TiposDocumentos entity) throws Exception {
		//Validamos que el tipo de documento no sea nulo
		if(entity==null)
			throw new Exception("Debe ingresar un tipo de documento");
		//Validamos que el c�digo del tipo de documento no sea nulo ni negativo
		if(entity.getTdocCodigo() <=0)
			throw new Exception("Debe ingresar un codigo valido para el tipo de documento");
		//Validamos que el tipo de documento exista
		TiposDocumentos tipo = tipoDocumentoDAO.findById(entity.getTdocCodigo());
		if(tipo == null)
			throw new Exception("El tipo de documento no existe y no se puede eliminar");
		//Eliminamos el tipo de documento
		tipoDocumentoDAO.delete(entity);
	}

	@Override
	public TiposDocumentos findById(Long id) throws Exception {
		//Verificamos que el id sea valido
		if(id<= 0)
			throw new Exception("Debe ingresar una identificacion de tipo de documento  valida");
		//buscamos el tipo de documento
		TiposDocumentos tipo = tipoDocumentoDAO.findById(id);
		if(tipo == null)
			throw new Exception("El tipo de documento no existe");
		return tipo;
	}

	@Override
	public List<TiposDocumentos> findAll() throws Exception {
		return tipoDocumentoDAO.findAll();
	}

}
