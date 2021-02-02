package com.praxis.staffy.controller;

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.praxis.staffy.model.dto.ClienteDto;
import com.praxis.staffy.model.dto.RespuestaGeneral;
import com.praxis.staffy.service.clientes.ClienteService;
import com.praxis.staffy.utilerias.GrupoValidaciones.ActualizarCliente;
import com.praxis.staffy.utilerias.GrupoValidaciones.CrearCliente;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = RecursosController.RAIZ_STAFFY)
@Slf4j
@Service
@AllArgsConstructor
public class ClienteController {
	
	private ClienteService clienteService;
	
	@GetMapping(path= RecursosController.PATH_CLIENTES)
	public ResponseEntity<RespuestaGeneral<?>>  obtenerClientes() {
		log.info("Controller clientes: find all");
		return new ResponseEntity<RespuestaGeneral<?>>(clienteService.findAllClientes(), HttpStatus.OK);
	}
	
	@GetMapping(path= RecursosController.PATH_CLIENTES_FIND_BYID)
	public ResponseEntity<RespuestaGeneral<?>>  obtenerClientesByID(@PathVariable(name = "idCliente", required = true) long idCliente) {
		log.info("Controller clientes: find by ID");
		return new ResponseEntity<RespuestaGeneral<?>>(clienteService.findByID(idCliente), HttpStatus.OK);
	}
	
	@PostMapping(path= RecursosController.PATH_CLIENTES_ADD)
	public ResponseEntity<RespuestaGeneral<?>>  agregarCliente(@Validated(value = {CrearCliente.class}) @RequestBody ClienteDto cliente) throws SerialException, SQLException {
		log.info("Controller clientes: Add cliente");
		return new ResponseEntity<RespuestaGeneral<?>>(clienteService.addCustomer(cliente), HttpStatus.CREATED);
	}
	
	@GetMapping(path= RecursosController.PATH_CLIENTES_NOMBRE)
	public ResponseEntity<RespuestaGeneral<?>>  obtenerClientesByNombre(@RequestParam(name = "nombre", required = false, defaultValue = "")  String nombre) {
		log.info("Controller clientes: find by nombre {}", nombre);
		return new ResponseEntity<RespuestaGeneral<?>>(clienteService.realizarFiltroByNombre(nombre), HttpStatus.OK);
	}
	
	@PutMapping(path= RecursosController.PATH_CLIENTES_ADD)
	public ResponseEntity<RespuestaGeneral<?>>  actualizarCliente(@Validated(value = {ActualizarCliente.class}) @RequestBody ClienteDto cliente) throws SerialException, SQLException {
		log.info("Controller clientes: Update cliente");
		return new ResponseEntity<RespuestaGeneral<?>>(clienteService.updateCustomer(cliente), HttpStatus.OK);
	}
	
}
