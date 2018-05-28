package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;
import co.edu.icesi.PlataformaBanco.modelo.Usuarios;

public interface IUsuariosDAO {

	public void save(Usuarios entity);
	public void update(Usuarios entity);
	public Usuarios findById(long id);
	public List<Usuarios> findAll();
	public void delete(Usuarios entity);
}
