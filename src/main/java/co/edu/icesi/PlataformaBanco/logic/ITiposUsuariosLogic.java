package co.edu.icesi.PlataformaBanco.logic;

import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.TiposUsuarios;

public interface ITiposUsuariosLogic {

	public void save(TiposUsuarios entity) throws Exception ;
	public void update(TiposUsuarios entity) throws Exception ;
	public TiposUsuarios findById(Long id) throws Exception ;
	public List<TiposUsuarios> findAll() throws Exception ;
}
