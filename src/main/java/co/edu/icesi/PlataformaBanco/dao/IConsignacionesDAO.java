package co.edu.icesi.PlataformaBanco.dao;

import java.util.Date;
import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.Consignaciones;
import co.edu.icesi.PlataformaBanco.modelo.ConsignacionesId;
import co.edu.icesi.PlataformaBanco.modelo.Cuentas;



public interface IConsignacionesDAO {

	public void save(Consignaciones entity);
	public void update(Consignaciones entity);
	public Consignaciones findById(ConsignacionesId id);
	public List<Consignaciones> findAll();
	public void invalidate(Consignaciones entity);
	List<Consignaciones> consultarConsignacionesPorCliente(long cedulaCliente);
	List<Consignaciones> consultarConsignacionesPorClientePorRangoFechas(long cedulaCliente, Date Inicio, Date fin);
	List<Consignaciones> consultarConsignacionesPorClientePorRangoFechasPorCuenta(long cedulaCliente, Date inicio,Date fin, Cuentas cuenta);
}
