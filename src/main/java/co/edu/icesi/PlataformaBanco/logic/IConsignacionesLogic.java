package co.edu.icesi.PlataformaBanco.logic;

import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.Consignaciones;
import co.edu.icesi.PlataformaBanco.modelo.ConsignacionesId;

public interface IConsignacionesLogic {

	public void save(Consignaciones entity) throws Exception ;
	public Consignaciones findById(ConsignacionesId id) throws Exception ;
	public List<Consignaciones> findAll() throws Exception ;
	List<Consignaciones> consultarConsignacionesPorCliente(long cedulaCliente);
}
