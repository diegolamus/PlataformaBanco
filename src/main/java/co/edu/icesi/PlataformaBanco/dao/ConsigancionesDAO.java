package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import co.edu.icesi.PlataformaBanco.modelo.Consignaciones;
import co.edu.icesi.PlataformaBanco.modelo.ConsignacionesId;

@Repository
@Scope("singleton")
public class ConsigancionesDAO implements IConsignacionesDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void save(Consignaciones entity) {
		em.persist(entity);	
		
	}

	@Override
	public void update(Consignaciones entity) {
		em.merge(entity);
	}

	@Override
	public Consignaciones findById(ConsignacionesId id) {
		return em.find(Consignaciones.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Consignaciones> findAll() {
		String jpql = "SELECT Con from Consignaciones Con";
		return em.createQuery(jpql).getResultList();
	}

	@Override
	public void invalidate(Consignaciones entity) {
		em.merge(entity);	
	}

}
