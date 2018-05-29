package co.edu.icesi.PlataformaBanco.businessDelegate;

import java.util.Date;
import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.Clientes;
import co.edu.icesi.PlataformaBanco.modelo.Consignaciones;
import co.edu.icesi.PlataformaBanco.modelo.Cuentas;
import co.edu.icesi.PlataformaBanco.modelo.Retiros;
import co.edu.icesi.PlataformaBanco.modelo.TiposDocumentos;
import co.edu.icesi.PlataformaBanco.modelo.Transferencias;
import co.edu.icesi.PlataformaBanco.modelo.Usuarios;


public interface IBusinessDelegate {

	//Operaciones cliente
	public void crearCliente(Clientes entity) throws Exception;
	public void modificarCliente(Clientes entity) throws Exception;
	public void inactivarCliente(Clientes entity) throws Exception;
	public Clientes findClienteByID(Long id) throws Exception;
	public List<Clientes> findAllCliente() throws Exception;
	
	//Operaciones Cuentas
	public void crearCuenta(Cuentas entity) throws Exception;
	public void modificarCuenta(Cuentas entity) throws Exception;
	public void inactivarCuenta(Cuentas entity) throws Exception;
	public Cuentas findCuentaByID(String cueNumero) throws Exception;
	public List<Cuentas> findAllCuentas() throws Exception;
	
	//Operaciones retiros
	public void crearRetiro(Retiros entity) throws Exception;
	public List<Retiros> findAllRetiros() throws Exception;
	
	//Operaciones consignaciones
	public void crearConsigancion(Consignaciones entity) throws Exception;
	public List<Consignaciones> findAllConsiganciones() throws Exception;
	
	//Operaciones Transferencias
	public void crearTransferencia(Transferencias entity) throws Exception;
	public List<Transferencias> findAllTransferencia() throws Exception;
	
	//Operaciones tipos documentos
	public TiposDocumentos finTiposDocumentosByID(Long id) throws Exception;
	public List<TiposDocumentos> finAllTiposDocumentos() throws Exception;
	
	//Operaciones usuarios
	public Usuarios findUsuarioById(long id) throws Exception;
	
	//Consultas
	List<Cuentas> consultarCuentasPorCliente(long cedulaCliente);
	List<Consignaciones> consultarConsignacionesPorCliente(long cedulaCliente);
	List<Retiros> consultarRetirosPorCliente(long cedulaCliente);
	List<Transferencias> consultarTransferenciasPorCliente(long cedulaCliente);
	List<Consignaciones> consultarConsignacionesPorClientePorRangoFechas(long cedulaCliente, Date Inicio, Date fin);
	List<Retiros> consultarRetirosPorClientePorRangoFechas(long cedulaCliente, Date Inicio, Date fin);
	List<Transferencias> consultarTransferenciasPorClientePorRangoFechas(long cedulaCliente, Date Inicio, Date fin);
	List<Transferencias> consultarTransferenciasPorClientePorRangoFechasPorCuenta(long cedulaCliente, Date inicio,Date fin, Cuentas cuenta) throws Exception;
	List<Consignaciones> consultarConsignacionesPorClientePorRangoFechasPorCuenta(long cedulaCliente, Date inicio,Date fin, Cuentas cuenta) throws Exception;
	List<Retiros> consultarRetirosPorClientePorRangoFechasPorCuenta(long cedulaCliente, Date Inicio, Date fin,Cuentas cuenta) throws Exception;
	
}
