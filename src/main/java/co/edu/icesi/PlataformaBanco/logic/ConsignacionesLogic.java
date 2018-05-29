package co.edu.icesi.PlataformaBanco.logic;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.PlataformaBanco.dao.IConsignacionesDAO;
import co.edu.icesi.PlataformaBanco.dao.ICuentasDAO;
import co.edu.icesi.PlataformaBanco.dao.IUsuariosDAO;
import co.edu.icesi.PlataformaBanco.modelo.Consignaciones;
import co.edu.icesi.PlataformaBanco.modelo.ConsignacionesId;
import co.edu.icesi.PlataformaBanco.modelo.Cuentas;
import co.edu.icesi.PlataformaBanco.modelo.Usuarios;

@Service
@Scope("singleton")
public class ConsignacionesLogic implements IConsignacionesLogic {

	@Autowired
	private IConsignacionesDAO consignacionesDAO;
	@Autowired
	private ICuentasDAO cuentasDAO;
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public void save(Consignaciones entity) throws Exception {
		//Validamos que la consignacion no sea nula
		if(entity == null)
			throw new Exception("Se debe ingresar una consignación");
		//Validamos que el id de la consignación no sea nulo
		if(entity.getId()==null)
			throw new Exception("Debe ingresar una numero de consignación valida");
		//Asignamos el numero de la consignacion
		long max = 0;
		List<Consignaciones> consig = consignacionesDAO.findAll();
		for (Consignaciones consignaciones : consig) {
			max = (consignaciones.getId().getConCodigo()>max)? max = consignaciones.getId().getConCodigo(): max;
		}
		entity.getId().setConCodigo(max+1);
		//Validamos que el id de la consugnación sea valido
		if(entity.getId().getConCodigo() == 0 || entity.getId().getCueNumero().trim().equals(""))
			throw new Exception("El id de la consignación no es valido");
		//Validamos que la consiganción tenga una cuenta asociada
		if(entity.getCuentas()==null)
			throw new Exception("La consignación debe tener una cuenta asociada");
		//Validamos que la cuenta asociada coincida con el numero en el id
		if(!entity.getCuentas().getCueNumero().equals(entity.getId().getCueNumero()))
			throw new Exception("La cuenta no coincide con el numero de cuenta");
		//Validamos que la cuenta exista
		Cuentas cuenta = cuentasDAO.findById(entity.getCuentas().getCueNumero());
		if(cuenta==null)
			throw new Exception("La cuenta no existe");
		//Validamos que el usuario asociado a la consignacion no sea nulo
		if(entity.getUsuarios()==null)
			throw new Exception("La consignación debe tener un usuario asociado");
		//Validamos que el usuario asociado a la consignacion exista
		Usuarios usuario = usuariosDAO.findById(entity.getUsuarios().getUsuCedula());
		if(usuario==null)
			throw new Exception("El usuario no existe");
		//Validamos que el valor de la consignacion no sea nulo o cero
		if(entity.getConValor()==null)
			throw new Exception("El valor ingresado no es valido");
		if(entity.getConValor().compareTo(new BigDecimal(0))<=0)
			throw new Exception("El valor de la consignacion debe ser mayor a cero");
		//Validamos que la fecha no sea nula
		if(entity.getConFecha()== null)
			throw new Exception("Debe ingresar una fecha");
		//Aumentamos fondos de la cuenta
		cuenta.setCueSaldo(cuenta.getCueSaldo().add(entity.getConValor()));
		cuentasDAO.update(cuenta);
		consignacionesDAO.save(entity);	
	}

	@Override
	@Transactional(readOnly = true)
	public Consignaciones findById(ConsignacionesId id) throws Exception {
		//Validamos que la id no sea nula
		if(id==null)
			throw new Exception("Debe ingresar un id valido");
		if(id.getConCodigo()==0)
			throw new Exception("Debe ingresar un codigo valido");
		if(id.getCueNumero()==null)
			throw new Exception("Debe ingresar un numero de cuenta valido");
		if(id.getCueNumero().trim().equals(""))
			throw new Exception("Debe ingresar un numero de cuenta valido");
		//Validamos que la consignación exista
		Consignaciones consig = consignacionesDAO.findById(id);
		if(consig==null)
			throw new Exception("La consignación no existe");
		return consig;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Consignaciones> findAll() throws Exception {
		return consignacionesDAO.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Consignaciones> consultarConsignacionesPorCliente(long cedulaCliente){
		return consignacionesDAO.consultarConsignacionesPorCliente(cedulaCliente);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Consignaciones> consultarConsignacionesPorClientePorRangoFechas(long cedulaCliente,Date Inicio,Date fin){
		return consignacionesDAO.consultarConsignacionesPorClientePorRangoFechas(cedulaCliente, Inicio, fin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Consignaciones> consultarConsignacionesPorClientePorRangoFechasPorCuenta(long cedulaCliente, Date inicio, Date fin, Cuentas cuenta) throws Exception{
		if(cuenta.getClientes().getCliId()!= cedulaCliente)
			throw new Exception("La cuenta ingresada no pertenece al cliente");
		return consignacionesDAO.consultarConsignacionesPorClientePorRangoFechasPorCuenta(cedulaCliente, inicio, fin, cuenta);
	}
}
