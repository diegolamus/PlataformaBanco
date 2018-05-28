package co.edu.icesi.PlataformaBanco.logic;

import java.util.List;
import co.edu.icesi.PlataformaBanco.modelo.Transferencias;
import co.edu.icesi.PlataformaBanco.modelo.TransferenciasId;

public interface ITransferenciasLogic {

	public void save(Transferencias entity) throws Exception ;
	public Transferencias findById(TransferenciasId id) throws Exception ;
	public List<Transferencias> findAll() throws Exception ;
}
