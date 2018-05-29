package co.edu.icesi.PlataformaBanco.logic;

import java.util.Date;
import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.Consignaciones;
import co.edu.icesi.PlataformaBanco.modelo.ConsignacionesId;
import co.edu.icesi.PlataformaBanco.modelo.Cuentas;

public interface IConsignacionesLogic {

	public void save(Consignaciones entity) throws Exception ;
	public Consignaciones findById(ConsignacionesId id) throws Exception ;
	public List<Consignaciones> findAll() throws Exception ;
	List<Consignaciones> consultarConsignacionesPorCliente(long cedulaCliente);
	List<Consignaciones> consultarConsignacionesPorClientePorRangoFechas(long cedulaCliente, Date Inicio, Date fin);
	List<Consignaciones> consultarConsignacionesPorClientePorRangoFechasPorCuenta(long cedulaCliente, Date inicio,Date fin, Cuentas cuenta) throws Exception;
}
