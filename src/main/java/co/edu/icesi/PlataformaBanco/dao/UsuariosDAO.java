package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import co.edu.icesi.PlataformaBanco.modelo.Usuarios;

@Repository
@Scope("singleton")
public class UsuariosDAO implements IUsuariosDAO{

	@PersistenceContext
	private EntityManager em;
	@Override
	public void save(Usuarios entity) {
		em.persist(entity);	
		
	}
	@Override
	public void update(Usuarios entity) {
		em.merge(entity);	
	}
	@Override
	public Usuarios findById(long id) {
		return em.find(Usuarios.class, id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuarios> findAll() {
		String jpql = "SELECT usu from Usuarios usu";
		return em.createQuery(jpql).getResultList();
	}
	@Override
	public void delete(Usuarios entity) {
		em.remove(entity);
		
	}


}
