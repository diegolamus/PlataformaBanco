package co.edu.icesi.PlataformaBanco.logic;

import java.util.Date;
import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.Cuentas;
import co.edu.icesi.PlataformaBanco.modelo.Retiros;
import co.edu.icesi.PlataformaBanco.modelo.RetirosId;

public interface IRetirosLogic {

	public void save(Retiros entity) throws Exception ;
	public Retiros findById(RetirosId id) throws Exception ;
	public List<Retiros> findAll() throws Exception ;
	List<Retiros> consultarRetirosPorCliente(long cedulaCliente);
	List<Retiros> consultarRetirosPorClientePorRangoFechas(long cedulaCliente, Date Inicio, Date fin);
	List<Retiros> consultarRetirosPorClientePorRangoFechasPorCuenta(long cedulaCliente, Date Inicio, Date fin,Cuentas cuenta) throws Exception;
}
