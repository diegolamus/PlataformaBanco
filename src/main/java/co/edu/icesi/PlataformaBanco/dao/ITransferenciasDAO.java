package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;

import co.edu.icesi.PlataformaBanco.modelo.Transferencias;
import co.edu.icesi.PlataformaBanco.modelo.TransferenciasId;

public interface ITransferenciasDAO {

	public void save(Transferencias entity);
	public void update(Transferencias entity);
	public Transferencias findById(TransferenciasId id);
	public List<Transferencias> findAll();
	public void invalidate(Transferencias entity);
}
