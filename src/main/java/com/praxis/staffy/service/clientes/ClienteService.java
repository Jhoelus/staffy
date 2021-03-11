package com.praxis.staffy.service.clientes;

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import com.praxis.staffy.model.dto.ClienteDto;
import com.praxis.staffy.model.dto.RespuestaGeneral;

public interface ClienteService {
	
	RespuestaGeneral<?> findAllClientes();
	RespuestaGeneral<?> findByID(long idCliente);
	RespuestaGeneral<?> addCustomer(ClienteDto cliente) throws SerialException, SQLException;
	RespuestaGeneral<?> realizarFiltroByNombre(String nombreLike);
	RespuestaGeneral<?> updateCustomer(ClienteDto cliente) throws SerialException, SQLException;
	RespuestaGeneral<?> deleteCustomer(long idCliente) throws SerialException, SQLException;
}
