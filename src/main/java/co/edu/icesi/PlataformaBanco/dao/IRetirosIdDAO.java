package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.RetirosId;

public interface IRetirosIdDAO {

	public void save(RetirosId entity);
	public void update(RetirosId entity);
	public RetirosId findById(long id);
	public List<RetirosId> findAll();
	public void invalidate(RetirosId entity);
}
