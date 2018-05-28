package co.edu.icesi.PlataformaBanco.logic;

import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.Cuentas;

public interface ICuentasLogic {

	public void save(Cuentas entity) throws Exception ;
	public void update(Cuentas entity) throws Exception ;
	public void invalidate(Cuentas entity) throws Exception ;
	public Cuentas findById(String id) throws Exception ;
	public List<Cuentas> findAll() throws Exception ;
}
