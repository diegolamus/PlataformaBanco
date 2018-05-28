package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.Retiros;
import co.edu.icesi.PlataformaBanco.modelo.RetirosId;

public interface IRetirosDAO {

	public void save(Retiros entity);
	public void update(Retiros entity);
	public Retiros findById(RetirosId id);
	public List<Retiros> findAll();
	public void invalidate(Retiros entity);
}
