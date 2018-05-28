package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import co.edu.icesi.PlataformaBanco.modelo.TransferenciasId;

@Repository
@Scope("singleton")
public class TransferenciasIdDAO implements ITransferenciasIdDAO{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void save(TransferenciasId entity) {
		em.persist(entity);	
		
	}

	@Override
	public void update(TransferenciasId entity) {
		em.merge(entity);
		
	}

	@Override
	public TransferenciasId findById(long id) {
		return em.find(TransferenciasId.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransferenciasId> findAll() {
		String jpql = "SELECT tranId from TransferenciasId tranId";
		return em.createQuery(jpql).getResultList();
	}

	@Override
	public void invalidate(TransferenciasId entity) {
		em.merge(entity);
	}

}
