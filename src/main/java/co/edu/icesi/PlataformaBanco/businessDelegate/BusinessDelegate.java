package co.edu.icesi.PlataformaBanco.businessDelegate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import co.edu.icesi.PlataformaBanco.logic.IClienteLogic;
import co.edu.icesi.PlataformaBanco.logic.IConsignacionesLogic;
import co.edu.icesi.PlataformaBanco.logic.ICuentasLogic;
import co.edu.icesi.PlataformaBanco.logic.IRetirosLogic;
import co.edu.icesi.PlataformaBanco.logic.ITipoDocumentoLogic;
import co.edu.icesi.PlataformaBanco.logic.ITransferenciasLogic;
import co.edu.icesi.PlataformaBanco.logic.IUsuarioLogic;
import co.edu.icesi.PlataformaBanco.modelo.Clientes;
import co.edu.icesi.PlataformaBanco.modelo.Consignaciones;
import co.edu.icesi.PlataformaBanco.modelo.Cuentas;
import co.edu.icesi.PlataformaBanco.modelo.Retiros;
import co.edu.icesi.PlataformaBanco.modelo.TiposDocumentos;
import co.edu.icesi.PlataformaBanco.modelo.Transferencias;
import co.edu.icesi.PlataformaBanco.modelo.Usuarios;


@Component("businessDelegate")
@Scope("singleton")
public class BusinessDelegate implements IBusinessDelegate {

	@Autowired
	private IClienteLogic clienteLogic;
	@Autowired
	private ICuentasLogic cuentasLogic;
	@Autowired
	private IRetirosLogic retirosLogic;
	@Autowired
	private IConsignacionesLogic consignacionesLogic;
	@Autowired
	private ITransferenciasLogic transferencuasLogic;
	@Autowired
	private ITipoDocumentoLogic tiposDocumentosLogic;
	@Autowired
	private IUsuarioLogic usuariosLogic;
	
	@Override
	public void crearCliente(Clientes entity) throws Exception {
		clienteLogic.save(entity);
	}

	@Override
	public void modificarCliente(Clientes entity) throws Exception {
		clienteLogic.update(entity);
	}

	@Override
	public void inactivarCliente(Clientes entity) throws Exception {
		clienteLogic.invalidate(entity);
	}

	@Override
	public List<Clientes> findAllCliente() throws Exception {
		return clienteLogic.findAll();
	}

	@Override
	public void crearCuenta(Cuentas entity) throws Exception {
		cuentasLogic.save(entity);
		
	}

	@Override
	public void modificarCuenta(Cuentas entity) throws Exception {
		cuentasLogic.update(entity);
	}

	@Override
	public void inactivarCuenta(Cuentas entity) throws Exception {
		cuentasLogic.invalidate(entity);
	}

	@Override
	public List<Cuentas> findAllCuentas() throws Exception {
		return cuentasLogic.findAll();
	}

	@Override
	public void crearRetiro(Retiros entity) throws Exception {
		retirosLogic.save(entity);	
	}

	@Override
	public List<Retiros> findAllRetiros() throws Exception {
		return retirosLogic.findAll();
	}

	@Override
	public void crearConsigancion(Consignaciones entity) throws Exception {
		consignacionesLogic.save(entity);
	}

	@Override
	public List<Consignaciones> findAllConsiganciones() throws Exception {
		return consignacionesLogic.findAll();
	}

	@Override
	public void crearTransferencia(Transferencias entity) throws Exception {
		transferencuasLogic.save(entity);
	}

	@Override
	public List<Transferencias> findAllTransferencia() throws Exception {
		return transferencuasLogic.findAll();
	}

	@Override
	public Clientes findClienteByID(Long id) throws Exception {
		return clienteLogic.findById(id);
	}

	@Override
	public List<TiposDocumentos> finAllTiposDocumentos() throws Exception {
		return tiposDocumentosLogic.findAll();
	}

	@Override
	public TiposDocumentos finTiposDocumentosByID(Long id) throws Exception {
		return tiposDocumentosLogic.findById(id);
	}

	@Override
	public Cuentas findCuentaByID(String cueNumero) throws Exception {
		return cuentasLogic.findById(cueNumero);
		
	}

	@Override
	public Usuarios findUsuarioById(long id) throws Exception {
		return usuariosLogic.findById(id);
	}
	
	@Override
	public List<Cuentas> consultarCuentasPorCliente(long cedulaCliente){
		return cuentasLogic.consultarCuentasPorCliente(cedulaCliente);
	}

	@Override
	public List<Consignaciones> consultarConsignacionesPorCliente(long cedulaCliente){
		return consignacionesLogic.consultarConsignacionesPorCliente(cedulaCliente);
	}

	


}
