package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.Consignaciones;
import co.edu.icesi.PlataformaBanco.modelo.ConsignacionesId;



public interface IConsignacionesDAO {

	public void save(Consignaciones entity);
	public void update(Consignaciones entity);
	public Consignaciones findById(ConsignacionesId id);
	public List<Consignaciones> findAll();
	public void invalidate(Consignaciones entity);
	List<Consignaciones> consultarConsignacionesPorCliente(long cedulaCliente);
}
