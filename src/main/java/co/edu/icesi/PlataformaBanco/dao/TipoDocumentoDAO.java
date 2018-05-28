package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.PlataformaBanco.modelo.TiposDocumentos;

@Repository
@Scope("singleton")
public class TipoDocumentoDAO implements ITipoDocumentoDAO{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void save(TiposDocumentos entity) {
		em.persist(entity);	
		
	}

	@Override
	public void update(TiposDocumentos entity) {
		em.merge(entity);
	}

	@Override
	public void delete(TiposDocumentos entity) {
		em.remove(entity);
	}

	@Override
	public TiposDocumentos findById(Long id) {
		return em.find(TiposDocumentos.class, id);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TiposDocumentos> findAll() {
		String jpql = "SELECT Tip from TiposDocumentos Tip";
		return em.createQuery(jpql).getResultList();
	}

	@Override
	public void invalidate(TiposDocumentos entity) {
		em.merge(entity);	
	}

}
