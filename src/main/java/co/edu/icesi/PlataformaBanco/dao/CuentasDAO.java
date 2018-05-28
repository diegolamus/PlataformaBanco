package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import co.edu.icesi.PlataformaBanco.modelo.Cuentas;

@Repository
@Scope("singleton")
public class CuentasDAO implements ICuentasDAO{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void save(Cuentas entity) {
		em.persist(entity);	
		
	}

	@Override
	public void update(Cuentas entity) {
		em.merge(entity);
		
	}

	@Override
	public void delete(Cuentas entity) {
		em.remove(entity);
		
	}

	@Override
	public Cuentas findById(String id) {
		return em.find(Cuentas.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cuentas> findAll() {
		String jpql = "SELECT Cue from Cuentas Cue";
		return em.createQuery(jpql).getResultList();
	}

	@Override
	public void invalidate(Cuentas entity) {
		em.merge(entity);
		
	}

}