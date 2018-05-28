package co.edu.icesi.PlataformaBanco.logic;

import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.Retiros;
import co.edu.icesi.PlataformaBanco.modelo.RetirosId;

public interface IRetirosLogic {

	public void save(Retiros entity) throws Exception ;
	public Retiros findById(RetirosId id) throws Exception ;
	public List<Retiros> findAll() throws Exception ;
}
