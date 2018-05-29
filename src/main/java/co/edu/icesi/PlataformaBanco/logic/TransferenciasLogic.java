package co.edu.icesi.PlataformaBanco.logic;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.PlataformaBanco.dao.ICuentasDAO;
import co.edu.icesi.PlataformaBanco.dao.ITransferenciasDAO;
import co.edu.icesi.PlataformaBanco.dao.IUsuariosDAO;
import co.edu.icesi.PlataformaBanco.modelo.Cuentas;
import co.edu.icesi.PlataformaBanco.modelo.Transferencias;
import co.edu.icesi.PlataformaBanco.modelo.TransferenciasId;
import co.edu.icesi.PlataformaBanco.modelo.Usuarios;

@Service
@Scope("singleton")
public class TransferenciasLogic implements ITransferenciasLogic {

	@Autowired
	private ICuentasDAO cuentasDAO;
	@Autowired
	private IUsuariosDAO usuariosDAO;
	@Autowired
	private ITransferenciasDAO transferenciasDAO;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public void save(Transferencias entity) throws Exception {
		// Validamos que la transferencia no sea nula
		if (entity == null)
			throw new Exception("Se debe ingresar una transferencia");
		// Validamos que el id de la transferencia no sea nulo
		if (entity.getId() == null)
			throw new Exception("Debe ingresar una id valida");
		//Asignamos el numero de la transferencia
		long max = 0;
		List<Transferencias> transfer = transferenciasDAO.findAll();
		for (Transferencias tran : transfer) {
			max = (tran.getId().getTranCodigo()>max)? max = tran.getId().getTranCodigo(): max;
		}
		entity.getId().setTranCodigo(max+1);
		// Validamos que el id de la transferencia sea valido
		if (entity.getId().getTranCodigo() == 0 || entity.getId().getDestinoCueNumero() == null
				|| entity.getId().getDestinoCueNumero().trim().equals("") || entity.getId().getOrigenCueNumero() == null
				|| entity.getId().getOrigenCueNumero().trim().equals(""))
			throw new Exception("El id de la transferencia no es valido");
		// Validamos que la transferencia tenga una cuenta de destino asociada
		if (entity.getCuentasByDestinoCueNumero() == null)
			throw new Exception("La transferencia debe tener una cuenta de destino asociada");
		// Validamos que la transferencia tenga una cuenta de origen asociada
		if (entity.getCuentasByOrigenCueNumero() == null)
			throw new Exception("La transferencia debe tener una cuenta de origen asociada");
		// Validamos que la cuenta de destino asociada coincida con el numero en el id
		if (!entity.getId().getDestinoCueNumero().equals(entity.getCuentasByDestinoCueNumero().getCueNumero()))
			throw new Exception("La  cuenta de destino no coincide con el numero de cuenta asociado");
		// Validamos que la cuenta de origen asociada coincida con el numero en el id
		if (!entity.getId().getOrigenCueNumero().equals(entity.getCuentasByOrigenCueNumero().getCueNumero()))
			throw new Exception("La  cuenta de origen no coincide con el numero de cuenta asociado");
		// Validamos que la cuenta de destino exista
		Cuentas cuentaOrigen= cuentasDAO.findById(entity.getCuentasByOrigenCueNumero().getCueNumero());
		if (cuentaOrigen == null)
			throw new Exception("La cuenta de destino no existe");
		// Validamos que la cuenta de origen exista
		Cuentas cuentaDestino = cuentasDAO.findById(entity.getCuentasByDestinoCueNumero().getCueNumero());
		if (cuentaDestino == null)
			throw new Exception("La cuenta de origen no existe");
		// Validamos que el usuario asociado a la transferencia no sea nulo
		if (entity.getUsuCedula() == 0)
			throw new Exception("La transferencia debe tener un usuario asociado");
		// Validamos que el usuario asociado a la transferencia exista
		Usuarios usuario = usuariosDAO.findById(entity.getUsuCedula());
		if (usuario == null)
			throw new Exception("El usuario  asociado a la transferencia no existe");
		// Validamos que el valor de la transferencia no sea nulo o cero
		if (entity.getTranValor() == null || entity.getTranValor().compareTo(new BigDecimal(0)) < 0)
			throw new Exception("El valor ingresado para la transferencia no es valido");
		// Validamos que la fecha no sea nula
		if (entity.getTranFecha() == null)
			throw new Exception("Debe ingresar una fecha");
		//Validamos que la cuenta de origen tenga fondos suficientes
		if(cuentaOrigen.getCueSaldo().subtract(entity.getTranValor()).compareTo(new BigDecimal("0"))<0)
			throw new Exception("La cuenta de origen no tiene fondos suficientes");
		//Validamos que la cuenta de origen no sea igual a la cuenta de detino
		if(cuentaOrigen.equals(cuentaDestino))
			throw new Exception("La cuenta de origen es igual a la cuenta de destino");
		//Disminuimos fondos de la cuenta origen
		cuentaOrigen.setCueSaldo(cuentaOrigen.getCueSaldo().subtract(entity.getTranValor()));
		//Aumentamos fondos de la cuenta destino
		cuentaDestino.setCueSaldo(cuentaDestino.getCueSaldo().add(entity.getTranValor()));
		//Actualizamos las cuentas
		cuentasDAO.update(cuentaDestino);
		cuentasDAO.update(cuentaOrigen);
		transferenciasDAO.save(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Transferencias findById(TransferenciasId id) throws Exception {
		// Validamos que el id de la transferencia no sea nulo
		if (id == null)
			throw new Exception("Se debe ingresar un id válido");
		// Validamos que el id de la transferencia sea valido
		if (id.getTranCodigo() == 0 || id.getDestinoCueNumero() == null
				|| id.getDestinoCueNumero().trim().equals("") || id.getOrigenCueNumero() == null
				|| id.getOrigenCueNumero().trim().equals(""))
			throw new Exception("El id de la transferencia no es valido");
		// Validamos que la transferencia exista
		Transferencias tran = transferenciasDAO.findById(id);
		if (tran == null)
			throw new Exception("La transferencia no existe");
		return tran;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Transferencias> findAll() throws Exception {
		return transferenciasDAO.findAll();
	}

}
