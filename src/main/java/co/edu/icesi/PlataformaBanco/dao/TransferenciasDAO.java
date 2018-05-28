package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.PlataformaBanco.modelo.Transferencias;
import co.edu.icesi.PlataformaBanco.modelo.TransferenciasId;

@Repository
@Scope("singleton")
public class TransferenciasDAO implements ITransferenciasDAO{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void save(Transferencias entity) {
		em.persist(entity);	
	}

	@Override
	public void update(Transferencias entity) {
		em.merge(entity);
		
	}

	@Override
	public Transferencias findById(TransferenciasId id) {
		return em.find(Transferencias.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transferencias> findAll() {
		String jpql = "SELECT Tran from Transferencias Tran";
		return em.createQuery(jpql).getResultList();
	}

	@Override
	public void invalidate(Transferencias entity) {
		em.merge(entity);
		
	}

}
