package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;


import co.edu.icesi.PlataformaBanco.modelo.TiposDocumentos;

public interface ITipoDocumentoDAO {

	public void save(TiposDocumentos entity);
	public void update(TiposDocumentos entity);
	public void delete(TiposDocumentos entity);
	public TiposDocumentos findById(Long id);
	public List<TiposDocumentos> findAll();
	public void invalidate(TiposDocumentos entity);
}
