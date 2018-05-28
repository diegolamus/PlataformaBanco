package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import co.edu.icesi.PlataformaBanco.modelo.Clientes;

@Repository
@Scope("singleton")
public class ClientesDAO implements IClientesDAO{

	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public void save(Clientes entity) {
		em.persist(entity);		
	}

	@Override
	public void update(Clientes entity) {
		em.merge(entity);
		
	}

	@Override
	public Clientes findById(Long id) {
		return em.find(Clientes.class, id);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Clientes> findAll() {
		String jpql = "SELECT Cli from Clientes Cli";
		return em.createQuery(jpql).getResultList();
	}

	@Override
	public void invalidate(Clientes entity) {
		em.merge(entity);	
	}

}
