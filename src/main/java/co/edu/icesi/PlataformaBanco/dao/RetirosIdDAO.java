package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.PlataformaBanco.modelo.RetirosId;

@Repository
@Scope("singleton")
public class RetirosIdDAO implements IRetirosIdDAO{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void save(RetirosId entity) {
		em.persist(entity);	
	}

	@Override
	public void update(RetirosId entity) {
		em.merge(entity);
	}

	@Override
	public RetirosId findById(long id) {
		return em.find(RetirosId.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RetirosId> findAll() {
		String jpql = "SELECT Ret from RetirosId Ret";
		return em.createQuery(jpql).getResultList();
	}

	@Override
	public void invalidate(RetirosId entity) {
		em.merge(entity);	
	}

}
