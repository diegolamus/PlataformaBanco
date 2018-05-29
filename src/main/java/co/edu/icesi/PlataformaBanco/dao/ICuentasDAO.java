package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.Cuentas;

public interface ICuentasDAO {

	public void save(Cuentas entity);
	public void update(Cuentas entity);
	public void delete(Cuentas entity);
	public Cuentas findById(String id);
	public List<Cuentas> findAll();
	public void invalidate(Cuentas entity);
	List<Cuentas> consultarCuentasPorCliente(long cedulaCliente);
}
