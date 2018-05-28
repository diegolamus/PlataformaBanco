package co.edu.icesi.PlataformaBanco.dao;
import java.util.List;
import co.edu.icesi.PlataformaBanco.modelo.Clientes;

public interface IClientesDAO {

	public void save(Clientes entity);
	public void update(Clientes entity);
	public Clientes findById(Long id);
	public List<Clientes> findAll();
	public void invalidate(Clientes entity);
	
	
}
