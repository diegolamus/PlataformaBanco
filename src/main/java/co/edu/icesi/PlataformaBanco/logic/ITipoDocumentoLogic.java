package co.edu.icesi.PlataformaBanco.logic;

import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.TiposDocumentos;

public interface ITipoDocumentoLogic {

	public void save(TiposDocumentos entity) throws Exception ;
	public void update(TiposDocumentos entity) throws Exception ;
	public void delete(TiposDocumentos entity) throws Exception ;
	public TiposDocumentos findById(Long id) throws Exception ;
	public List<TiposDocumentos> findAll() throws Exception ;
}
