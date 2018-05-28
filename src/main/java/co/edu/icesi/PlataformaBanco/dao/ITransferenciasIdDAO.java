package co.edu.icesi.PlataformaBanco.dao;

import java.util.List;
import co.edu.icesi.PlataformaBanco.modelo.TransferenciasId;

public interface ITransferenciasIdDAO {
	public void save(TransferenciasId entity);
	public void update(TransferenciasId entity);
	public TransferenciasId findById(long id);
	public List<TransferenciasId> findAll();
	public void invalidate(TransferenciasId entity);
}
