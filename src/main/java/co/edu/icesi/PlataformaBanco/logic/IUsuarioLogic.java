package co.edu.icesi.PlataformaBanco.logic;

import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.Usuarios;

public interface IUsuarioLogic {

	public void save(Usuarios entity) throws Exception ;
	public void update(Usuarios entity) throws Exception ;
	public Usuarios findById(long id) throws Exception ;
	public List<Usuarios> findAll() throws Exception ;
	public void delete(Usuarios entity) throws Exception ;
}
