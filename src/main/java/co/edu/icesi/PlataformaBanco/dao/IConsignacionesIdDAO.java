package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.ConsignacionesId;

public interface IConsignacionesIdDAO {

	public void save(ConsignacionesId entity);
	public void update(ConsignacionesId entity);
	public ConsignacionesId findById(Long id);
	public List<ConsignacionesId> findAll();
	public void invalidate(ConsignacionesId entity);
}
