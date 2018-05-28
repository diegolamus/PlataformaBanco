package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import co.edu.icesi.PlataformaBanco.modelo.Retiros;
import co.edu.icesi.PlataformaBanco.modelo.RetirosId;

@Repository
@Scope("singleton")
public class RetirosDAO implements IRetirosDAO{

	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public void save(Retiros entity) {
		em.persist(entity);	
		
	}

	@Override
	public void update(Retiros entity) {
		em.merge(entity);
		
	}

	@Override
	public Retiros findById(RetirosId id) {
		return em.find(Retiros.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Retiros> findAll() {
		String jpql = "SELECT Ret from Retiros Ret";
		return em.createQuery(jpql).getResultList();
	}

	@Override
	public void invalidate(Retiros entity) {
		em.merge(entity);
	}

}
