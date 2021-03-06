package co.edu.icesi.PlataformaBanco.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import co.edu.icesi.PlataformaBanco.modelo.Consignaciones;
import co.edu.icesi.PlataformaBanco.modelo.ConsignacionesId;
import co.edu.icesi.PlataformaBanco.modelo.Cuentas;

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
	
	@Override
	public List<Consignaciones> consultarConsignacionesPorCliente(long cedulaCliente){
		String jpql = "SELECT Con FROM Consignaciones Con WHERE Con.cuentas.clientes.cliId=:cedulaCliente";
		Query query = em.createQuery(jpql);
		query.setParameter("cedulaCliente", cedulaCliente);
		@SuppressWarnings("unchecked")
		List<Consignaciones> conisgnaciones = (List<Consignaciones>) query.getResultList();
		return conisgnaciones;
	}
	
	@Override
	public List<Consignaciones> consultarConsignacionesPorClientePorRangoFechas(long cedulaCliente, Date Inicio, Date fin){
		String jpql = "SELECT Con FROM Consignaciones Con WHERE Con.cuentas.clientes.cliId=:cedulaCliente"
				+ " AND Con.conFecha BETWEEN :start AND :end ";
		Query query = em.createQuery(jpql);
		query.setParameter("cedulaCliente", cedulaCliente);
		query.setParameter("start", Inicio);
		query.setParameter("end", fin);
		@SuppressWarnings("unchecked")
		List<Consignaciones> conisgnaciones = (List<Consignaciones>) query.getResultList();
		return conisgnaciones;
	}
	
	@Override
	public List<Consignaciones> consultarConsignacionesPorClientePorRangoFechasPorCuenta(long cedulaCliente, Date inicio, Date fin, Cuentas cuenta){
		String jpql = "SELECT Con FROM Consignaciones Con WHERE Con.cuentas.clientes.cliId=:cedulaCliente"
				+ " AND Con.cuentas=:cuenta"+" AND Con.conFecha BETWEEN :start AND :end" ;
		Query query = em.createQuery(jpql);
		query.setParameter("cedulaCliente", cedulaCliente);
		query.setParameter("start", inicio);
		query.setParameter("end", fin);
		query.setParameter("cuenta", cuenta);
		@SuppressWarnings("unchecked")
		List<Consignaciones> conisgnaciones = (List<Consignaciones>) query.getResultList();
		return conisgnaciones;
	}
	
	

}
