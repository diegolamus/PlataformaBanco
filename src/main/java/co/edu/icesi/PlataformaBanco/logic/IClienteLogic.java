package co.edu.icesi.PlataformaBanco.logic;

import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.Clientes;

public interface IClienteLogic {

	public void save(Clientes entity) throws Exception ;
	public void update(Clientes entity) throws Exception ;
	public void invalidate(Clientes entity) throws Exception ;
	public Clientes findById(Long id) throws Exception ;
	public List<Clientes> findAll() throws Exception ;
	
}
