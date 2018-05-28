package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.PlataformaBanco.modelo.TiposUsuarios;

@Repository
@Scope("singleton")
public class TiposUsuariosDAO implements ITiposUsuariosDAO {


	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void save(TiposUsuarios entity) {
		em.persist(entity);	
	}

	@Override
	public void update(TiposUsuarios entity) {
		em.merge(entity);
	}

	@Override
	public TiposUsuarios findById(long id) {
		return em.find(TiposUsuarios.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TiposUsuarios> findAll() {
		String jpql = "SELECT tusu from TiposUsuarios tusu";
		return em.createQuery(jpql).getResultList();
	}

	@Override
	public void invalidate(TiposUsuarios entity) {
		em.merge(entity);	
	}

}
