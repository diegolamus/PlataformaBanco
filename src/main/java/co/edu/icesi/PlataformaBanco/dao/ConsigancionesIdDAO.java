package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.PlataformaBanco.modelo.ConsignacionesId;

@Repository
@Scope("singleton")
public class ConsigancionesIdDAO implements IConsignacionesIdDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void save(ConsignacionesId entity) {
		em.persist(entity);	
		
	}

	@Override
	public void update(ConsignacionesId entity) {
		em.merge(entity);
		
	}

	@Override
	public ConsignacionesId findById(Long id) {
		return em.find(ConsignacionesId.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignacionesId> findAll() {
		String jpql = "SELECT Con from ConsignacionesId Con";
		return em.createQuery(jpql).getResultList();
	}

	@Override
	public void invalidate(ConsignacionesId entity) {
		em.merge(entity);
	}

}
