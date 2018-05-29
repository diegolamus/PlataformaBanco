package co.edu.icesi.PlataformaBanco.logic;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.PlataformaBanco.dao.ICuentasDAO;
import co.edu.icesi.PlataformaBanco.dao.IRetirosDAO;
import co.edu.icesi.PlataformaBanco.dao.IUsuariosDAO;
import co.edu.icesi.PlataformaBanco.modelo.Cuentas;
import co.edu.icesi.PlataformaBanco.modelo.Retiros;
import co.edu.icesi.PlataformaBanco.modelo.RetirosId;
import co.edu.icesi.PlataformaBanco.modelo.Usuarios;

@Service
@Scope("singleton")
public class RetirosLogic implements IRetirosLogic {

	@Autowired
	private IRetirosDAO retirosDAO;
	@Autowired
	private ICuentasDAO cuentasDAO;
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public void save(Retiros entity) throws Exception {
		// Validamos que la consignacion no sea nula
		if (entity == null)
			throw new Exception("Se debe ingresar un retiro");
		// Validamos que el id de la consignación no sea nulo
		if (entity.getId() == null)
			throw new Exception("Debe ingresar una id valida");
		//Asignamos el numero del retiro
		long max = 0;
		List<Retiros> retiros = retirosDAO.findAll();
		for (Retiros ret : retiros) {
			max = (ret.getId().getRetCodigo()>max)? max = ret.getId().getRetCodigo(): max;
		}
		entity.getId().setRetCodigo(max+1);
		// Validamos que el id de la consugnación sea valido
		if (entity.getId().getRetCodigo() == 0 || entity.getId().getCueNumero().trim().equals(""))
			throw new Exception("El id del retiro no es valido");
		// Validamos que la consiganción tenga una cuenta asociada
		if (entity.getCuentas() == null)
			throw new Exception("El retiro debe tener una cuenta asociada");
		// Validamos que la cuenta asociada coincida con el numero en el id
		if (!entity.getCuentas().getCueNumero().equals(entity.getId().getCueNumero()))
			throw new Exception("La cuenta no coincide con el numero de cuenta");
		// Validamos que la cuenta exista
		Cuentas cuenta = cuentasDAO.findById(entity.getCuentas().getCueNumero());
		if (cuenta == null)
			throw new Exception("La cuenta no existe");
		// Validamos que el usuario asociado al retiro no sea nulo
		if (entity.getUsuarios() == null)
			throw new Exception("La consignación debe tener un usuario asociado");
		// Validamos que el usuario asociado al retiro exista
		Usuarios usuario = usuariosDAO.findById(entity.getUsuarios().getUsuCedula());
		if (usuario == null)
			throw new Exception("El usuario no existe");
		// Validamos que el valor del retiro no sea nulo o cero
		if (entity.getRetValor() == null)
			throw new Exception("El valor ingresado no es valido");
		if (entity.getRetValor().compareTo(new BigDecimal(0)) < 0)
			throw new Exception("El valor de la consignacion debe ser mayor a cero");
		// Validamos que la fecha no sea nula
		if (entity.getRetFecha() == null)
			throw new Exception("Debe ingresar una fecha");
		//Validamos que la cuenta tenga fondos suficientes
		if(cuenta.getCueSaldo().subtract(entity.getRetValor()).compareTo(new BigDecimal("0"))<0)
			throw new Exception("La cuenta no tiene fondos suficientes");
		//Disminuimos fondos de la cuenta
		cuenta.setCueSaldo(cuenta.getCueSaldo().subtract(entity.getRetValor()));
		cuentasDAO.update(cuenta);
		retirosDAO.save(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Retiros findById(RetirosId id) throws Exception {
		//Validamos que la id no sea nula
		if(id==null)
			throw new Exception("Debe ingresar un id valido");
		if(id.getRetCodigo()==0)
			throw new Exception("Debe ingresar un codigo valido");
		if(id.getCueNumero()==null)
			throw new Exception("Debe ingresar un numero de cuenta valido");
		if(id.getCueNumero().trim().equals(""))
			throw new Exception("Debe ingresar un numero de cuenta valido");
		//Validamos que el retiro exista
		Retiros ret = retirosDAO.findById(id);
		if(ret==null)
			throw new Exception("La consignación no existe");
		return ret;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Retiros> findAll() throws Exception {
		return retirosDAO.findAll();
	}

}
