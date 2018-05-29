package co.edu.icesi.PlataformaBanco.logic;

import java.util.Date;
import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.Cuentas;
import co.edu.icesi.PlataformaBanco.modelo.Transferencias;
import co.edu.icesi.PlataformaBanco.modelo.TransferenciasId;

public interface ITransferenciasLogic {

	public void save(Transferencias entity) throws Exception ;
	public Transferencias findById(TransferenciasId id) throws Exception ;
	public List<Transferencias> findAll() throws Exception ;
	List<Transferencias> consultarTransferenciasPorCliente(long cedulaCliente);
	List<Transferencias> consultarTransferenciasPorClientePorRangoFechas(long cedulaCliente, Date Inicio, Date fin);
	List<Transferencias> consultarTransferenciasPorClientePorRangoFechasPorCuenta(long cedulaCliente, Date inicio,Date fin, Cuentas cuenta) throws Exception;
}
