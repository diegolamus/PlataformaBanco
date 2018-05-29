package co.edu.icesi.PlataformaBanco.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.PlataformaBanco.modelo.Cuentas;
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
	
	@Override
	public List<Retiros> consultarRetirosPorCliente(long cedulaCliente){
		String jpql = "SELECT Ret from Retiros Ret WHERE Ret.cuentas.clientes.cliId=:cedulaCliente";
		Query query = em.createQuery(jpql);
		query.setParameter("cedulaCliente", cedulaCliente);
		@SuppressWarnings("unchecked")
		List<Retiros> retiros = (List<Retiros>) query.getResultList();
		return retiros;
	}
	
	@Override
	public List<Retiros> consultarRetirosPorClientePorRangoFechas(long cedulaCliente, Date Inicio, Date fin){
		String jpql = "SELECT Ret from Retiros Ret WHERE Ret.cuentas.clientes.cliId=:cedulaCliente"
				+ " AND Ret.retFecha BETWEEN :start AND :end ";
		Query query = em.createQuery(jpql);
		query.setParameter("cedulaCliente", cedulaCliente);
		query.setParameter("start", Inicio);
		query.setParameter("end", fin);
		@SuppressWarnings("unchecked")
		List<Retiros> retiros = (List<Retiros>) query.getResultList();
		return retiros;
	}
	
	@Override
	public List<Retiros> consultarRetirosPorClientePorRangoFechasPorCuenta(long cedulaCliente, Date Inicio, Date fin, Cuentas cuenta){
		String jpql = "SELECT Ret from Retiros Ret WHERE Ret.cuentas.clientes.cliId=:cedulaCliente"
				+ " AND Con.cuentas=:cuenta"+" AND Ret.retFecha BETWEEN :start AND :end ";
		Query query = em.createQuery(jpql);
		query.setParameter("cedulaCliente", cedulaCliente);
		query.setParameter("start", Inicio);
		query.setParameter("end", fin);
		query.setParameter("cuenta", cuenta);
		@SuppressWarnings("unchecked")
		List<Retiros> retiros = (List<Retiros>) query.getResultList();
		return retiros;
	}

}
