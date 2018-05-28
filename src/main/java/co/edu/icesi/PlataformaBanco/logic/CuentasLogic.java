package co.edu.icesi.PlataformaBanco.logic;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.PlataformaBanco.dao.IClientesDAO;
import co.edu.icesi.PlataformaBanco.dao.ICuentasDAO;
import co.edu.icesi.PlataformaBanco.modelo.Clientes;
import co.edu.icesi.PlataformaBanco.modelo.Cuentas;

@Service
@Scope("singleton")
public class CuentasLogic implements ICuentasLogic{

	@Autowired
	private ICuentasDAO cuentasDAO;
	@Autowired
	private IClientesDAO clienteDAO;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public void save(Cuentas entity) throws Exception {
		//Validamos que la cuenta no sea nula
		if(entity == null)
			throw new Exception("Se debe ingresar una cuenta");
		//Validamos que el numero de cuenta no sea nulo ni vacio
		if(entity.getCueNumero() == null || entity.getCueNumero().trim().equals(""))
			throw new Exception("Se debe ingresar un numero de cuenta valido");
		//Validamos que el cliente asociado a la cuenta no sea nulo
		if(entity.getClientes() == null || entity.getClientes().getCliId()==0)
			throw new Exception("Debe ingresar un cliente para la cuenta");
		//Validamos que el cliente exista
		Clientes cliente = clienteDAO.findById(entity.getClientes().getCliId());
		if(cliente == null)
			throw new Exception("El cliente: " + entity.getClientes().getCliId() + " no existe y no se le puede agregar una cuenta");
		//Validamos que la cuenta se cree como activa
		if(entity.getCueActiva()== null || !entity.getCueActiva().equals("S"))
			throw new Exception("La cuenta debe crearso como activa");
		//Validamos que se ingrese una clave
		if(entity.getCueClave()== null || entity.getCueClave().trim().equals(""))
			throw new Exception("Se debe ingresar una clave");
		//verificamos que la cuenta no existe
		Cuentas cuenta = cuentasDAO.findById(entity.getCueNumero());
		if(cuenta != null)
			throw new Exception("La numero de cuenta ya existe y no se puede crear");
		//Validamos que la cuenta tenga un saldo superior a cero
		if(entity.getCueSaldo() == null ||entity.getCueSaldo().compareTo(new BigDecimal(0)) <= 0)
			throw new Exception("Se debe ingresar un saldo superior a cero");
		cuentasDAO.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public void update(Cuentas entity) throws Exception {
		
		//Validamos que la cuenta no sea nula
		if(entity == null)
			throw new Exception("Se debe ingresar una cuenta");
		//Validamos que el numero de cuenta no sea nulo ni vacio
		if(entity.getCueNumero() == null || entity.getCueNumero().trim().equals(""))
			throw new Exception("Se debe ingresar un numero de cuenta valido");
		//validamos que la cuenta existe
		Cuentas cuenta = cuentasDAO.findById(entity.getCueNumero());
		if(cuenta == null)
			throw new Exception("La cuenta no existe y no se puede modificar");
		//Validamos que el cliente asociado a el cliente no sea nulo
		if(entity.getClientes() == null || entity.getClientes().getCliId()==0)
			throw new Exception("Debe ingresar un cliente para la cuenta");
		//Validamos que el cliente exista
		Clientes cliente = clienteDAO.findById(entity.getClientes().getCliId());
		if(cliente == null)
			throw new Exception("El cliente: " + entity.getClientes().getCliId() + " no existe y no se le puede agregar una cuenta");
		//Validamos que el estado de la cuenta se guarde como S(activa) o N(inactiva)
		if(entity.getCueActiva()==null)
			throw new Exception("La cuenta debe estar como activa (S) o inactiva (N)");
		//Validamos que se ingrese una clave
		if(entity.getCueClave()== null || entity.getCueClave().trim().equals(""))
			throw new Exception("Se debe ingresar una clave");
		//Validamos que la cuenta tenga un saldo superior a cero
		if(entity.getCueSaldo() == null ||entity.getCueSaldo().compareTo(new BigDecimal(0)) <= 0)
			throw new Exception("Se debe ingresar un saldo superior a cero");
		cuentasDAO.update(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public void invalidate(Cuentas entity) throws Exception {
		//Validamos que la cuenta no sea nula
		if(entity == null)
			throw new Exception("Se debe ingresar una cuenta");
		//Validamos que el numero de cuenta no sea nulo ni vacio
		if(entity.getCueNumero() == null || entity.getCueNumero().trim().equals(""))
			throw new Exception("Se debe ingresar un numero de cuenta valido");
		//validamos que la cuenta existe
		Cuentas cuenta = cuentasDAO.findById(entity.getCueNumero());
		if(cuenta == null)
			throw new Exception("La cuenta no existe y no se puede modificar");
		//Validamos o invalidamos la cuenta
		if(entity.getCueActiva().trim().equals("S"))
			entity.setCueActiva("N");
		else
			entity.setCueActiva("S");
		cuentasDAO.update(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Cuentas findById(String id) throws Exception {
		Cuentas cuenta = cuentasDAO.findById(id);
		if(cuenta == null)
			throw new Exception("La cuenta no existe");
		return cuenta;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cuentas> findAll() throws Exception {
		return cuentasDAO.findAll();
	}

}
