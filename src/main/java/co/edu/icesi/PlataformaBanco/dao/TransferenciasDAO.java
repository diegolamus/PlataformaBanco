package co.edu.icesi.PlataformaBanco.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.PlataformaBanco.modelo.Cuentas;
import co.edu.icesi.PlataformaBanco.modelo.Transferencias;
import co.edu.icesi.PlataformaBanco.modelo.TransferenciasId;

@Repository
@Scope("singleton")
public class TransferenciasDAO implements ITransferenciasDAO{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void save(Transferencias entity) {
		em.persist(entity);	
	}

	@Override
	public void update(Transferencias entity) {
		em.merge(entity);
		
	}

	@Override
	public Transferencias findById(TransferenciasId id) {
		return em.find(Transferencias.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transferencias> findAll() {
		String jpql = "SELECT Tran from Transferencias Tran";
		return em.createQuery(jpql).getResultList();
	}

	@Override
	public void invalidate(Transferencias entity) {
		em.merge(entity);
		
	}
	
	@Override
	public List<Transferencias> consultarTransferenciasPorCliente(long cedulaCliente){
		String jpql = "SELECT Tran from Transferencias Tran WHERE Tran.cuentasByOrigenCueNumero.clientes.cliId=:cedulaCliente";
		Query query = em.createQuery(jpql);
		query.setParameter("cedulaCliente", cedulaCliente);
		@SuppressWarnings("unchecked")
		List<Transferencias> transfrenecias = (List<Transferencias>) query.getResultList();
		return transfrenecias;
	}
	
	@Override
	public List<Transferencias> consultarTransferenciasPorClientePorRangoFechas(long cedulaCliente, Date Inicio, Date fin){
		String jpql = "SELECT Tran from Transferencias Tran WHERE Tran.cuentasByOrigenCueNumero.clientes.cliId=:cedulaCliente"
				+ " AND Tran.tranFecha BETWEEN :start AND :end ";
		Query query = em.createQuery(jpql);
		query.setParameter("cedulaCliente", cedulaCliente);
		query.setParameter("start", Inicio);
		query.setParameter("end", fin);
		@SuppressWarnings("unchecked")
		List<Transferencias> transfrenecias = (List<Transferencias>) query.getResultList();
		return transfrenecias;
	}
	
	@Override
	public List<Transferencias> consultarTransferenciasPorClientePorRangoFechasPorCuenta(long cedulaCliente, Date inicio, Date fin, Cuentas cuenta){
		String jpql = "SELECT Tran from Transferencias Tran WHERE Tran.cuentasByOrigenCueNumero.clientes.cliId=:cedulaCliente"
				+ " AND Tran.cuentasByOrigenCueNumero=:cuenta"+ " AND Tran.tranFecha BETWEEN :start AND :end ";
		Query query = em.createQuery(jpql);
		query.setParameter("cedulaCliente", cedulaCliente);
		query.setParameter("start", inicio);
		query.setParameter("end", fin);
		query.setParameter("cuenta", cuenta);
		@SuppressWarnings("unchecked")
		List<Transferencias> transfrenecias = (List<Transferencias>) query.getResultList();
		return transfrenecias;
	}
	
	

}
