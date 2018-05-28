package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.TiposUsuarios;

public interface ITiposUsuariosDAO {

	public void save(TiposUsuarios entity);
	public void update(TiposUsuarios entity);
	public TiposUsuarios findById(long id);
	public List<TiposUsuarios> findAll();
	public void invalidate(TiposUsuarios entity);
}
