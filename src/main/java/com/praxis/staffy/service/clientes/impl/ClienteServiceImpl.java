package com.praxis.staffy.service.clientes.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.praxis.staffy.model.dto.ClienteDto;
import com.praxis.staffy.model.dto.DireccionDto;
import com.praxis.staffy.model.dto.RespuestaGeneral;
import com.praxis.staffy.model.entidad.ClienteEntity;
import com.praxis.staffy.model.entidad.DireccionEntity;
import com.praxis.staffy.repository.ClienteRepository;
import com.praxis.staffy.service.clientes.ClienteService;
import com.praxis.staffy.utilerias.GeneralUtils;
import com.praxis.staffy.utilerias.MensajesAplicacion;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService{

	private ClienteRepository clienteRepository;
	
	private ModelMapper mapper;
	
	@Override
	@Transactional
	public RespuestaGeneral<?> findAllClientes() {
		log.info("Servicio clientes: Find all");
		
		RespuestaGeneral<List<ClienteDto>> response = new RespuestaGeneral<List<ClienteDto>>();
		List<ClienteDto> clientesDTO = new ArrayList<ClienteDto>();
		
		List<ClienteEntity> clientesEntity =  (List<ClienteEntity>) clienteRepository.findAll();
		
		clientesEntity.forEach((ClienteEntity clienteBDD) -> {
			ClienteDto clienteDTO = mapper.map(clienteBDD, ClienteDto.class);
			clienteDTO.setImagen(clienteBDD.getImagen() != null ? GeneralUtils.clobToString(clienteBDD.getImagen()) : null);
			clientesDTO.add(clienteDTO);
		});
		
		response.setCode(MensajesAplicacion.MENSAJE_EXITO.getCodigo());
		response.setMessage(MensajesAplicacion.MENSAJE_EXITO.getMensaje());
		response.setData(clientesDTO);
		
		return response;
	}

	@Override
	@Transactional
	public RespuestaGeneral<?> findByID(long idCliente) {
		log.info("Servicio clientes: Find By ID");
		
		RespuestaGeneral<ClienteDto> response = new RespuestaGeneral<ClienteDto>();
		ClienteDto clienteDTO = null;
		
		if(clienteRepository.existsById(idCliente)) {
			ClienteEntity clienteBDD = clienteRepository.findById(idCliente).orElse(null);
			clienteDTO = mapper.map(clienteBDD, ClienteDto.class);
			clienteDTO.setImagen(clienteBDD.getImagen() != null ? GeneralUtils.clobToString(clienteBDD.getImagen()) : null);
		}
						
		response.setCode(MensajesAplicacion.MENSAJE_EXITO.getCodigo());
		response.setMessage(MensajesAplicacion.MENSAJE_EXITO.getMensaje());
		response.setData(clienteDTO);
		
		return response;
	}
	
	@Override
	@Transactional
	public RespuestaGeneral<?> addCustomer(ClienteDto cliente) throws SerialException, SQLException {
		log.info("Servicio clientes: Add cliente");
		
		RespuestaGeneral<ClienteDto> response = new RespuestaGeneral<ClienteDto>();
		ClienteEntity clienteEntity = mapper.map(cliente, ClienteEntity.class);
		
		//Si la imagen biene en base64 la pasamos a Clob
		if(cliente.getImagen() != null) {
			clienteEntity.setImagen(new javax.sql.rowset.serial.SerialClob((cliente.getImagen()).toCharArray()));
		}
		
		//Para que guarde la referencia del cliente en cada direccion
		clienteEntity.getDirecciones().forEach(direccion -> {
			direccion.setCliente(clienteEntity);
			direccion.setFechaCreacion(new Date());
		});
		
		clienteEntity.setFechaCreacion(new Date());
		ClienteEntity clienteBDD = clienteRepository.save(clienteEntity);
		
		ClienteDto clienteReturn = mapper.map(clienteBDD, ClienteDto.class);
		clienteReturn.setImagen(clienteBDD.getImagen() != null ? GeneralUtils.clobToString(clienteBDD.getImagen()) : null);
		
		response.setCode(MensajesAplicacion.MENSAJE_EXITO.getCodigo());
		response.setMessage(MensajesAplicacion.MENSAJE_EXITO.getMensaje());
		response.setData(clienteReturn);
		
		return response;
	}

	@Override
	@Transactional
	public RespuestaGeneral<?> realizarFiltroByNombre(String nombreLike) {
		
		log.info("Servicio clientes: Find like%nombre%");
		
		RespuestaGeneral<List<ClienteDto>> response = new RespuestaGeneral<List<ClienteDto>>();
		List<ClienteDto> clientesDTO = new ArrayList<>();
		
		List<ClienteEntity> clientesBDD = clienteRepository.findByNombreContaining(nombreLike);
		
		clientesBDD.forEach(clienteBD -> {
			ClienteDto clienteTempo = mapper.map(clienteBD, ClienteDto.class);
			clienteTempo.setImagen(clienteBD.getImagen() != null ? GeneralUtils.clobToString(clienteBD.getImagen()) : null);
			clientesDTO.add(clienteTempo);
		});
						
		response.setCode(MensajesAplicacion.MENSAJE_EXITO.getCodigo());
		response.setMessage(MensajesAplicacion.MENSAJE_EXITO.getMensaje());
		response.setData(clientesDTO);
		
		return response;
	}

	@Override
	@Transactional
	public RespuestaGeneral<?> updateCustomer(ClienteDto cliente) throws SerialException, SQLException {
		log.info("Servicio clientes: Update cliente");
		
		ClienteDto clienteDTO = null;
		RespuestaGeneral<ClienteDto> response = new RespuestaGeneral<ClienteDto>();
		
		if(clienteRepository.existsById(cliente.getIdCliente())) {
			ClienteEntity clienteBDD = clienteRepository.findById(cliente.getIdCliente()).orElse(null);
			
			clienteBDD.setNombre(cliente.getNombre());
			clienteBDD.setProyectosEspera(cliente.getProyectosEspera() != 0 ? cliente.getProyectosEspera() : clienteBDD.getProyectosEspera());
			clienteBDD.setProyectosConcluidos(cliente.getProyectosConcluidos() != 0 ? cliente.getProyectosConcluidos() : clienteBDD.getProyectosConcluidos());
			clienteBDD.setImagen(cliente.getImagen() != null && !cliente.getImagen().isEmpty() ? new javax.sql.rowset.serial.SerialClob((cliente.getImagen()).toCharArray()) : clienteBDD.getImagen());
			
			//Actualizamos las direcciones
			if(!cliente.getDirecciones().isEmpty()) {
				
				for(DireccionDto direccionDto  : cliente.getDirecciones() ) {
					boolean direccionExisteYPerteneceAlCliente = false;
					
					for(DireccionEntity direccionBDD: clienteBDD.getDirecciones()) {
						// Actualizamos direccion existente
						if(direccionDto.getIdDireccion() == direccionBDD.getIdDireccion()) {
							log.info("Actualizando informacion de Dto de la direccion {} ", direccionBDD.getIdDireccion());
							
							direccionBDD.setCalle(direccionDto.getCalle());
							direccionBDD.setNumero(direccionDto.getNumero());
							direccionBDD.setColonia(direccionDto.getColonia());
							direccionBDD.setCp(direccionDto.getCp());
							direccionBDD.setDelegacion(direccionDto.getDelegacion());
							direccionBDD.setEstado(direccionDto.getEstado());
							direccionBDD.setReferencia(direccionDto.getReferencia() != null ? direccionDto.getReferencia() : null);
							direccionExisteYPerteneceAlCliente = true;
						}
					}
					
					// Se agrega nueva direccion
					if(!direccionExisteYPerteneceAlCliente){
						DireccionEntity direccionEntity = new DireccionEntity();
						
						direccionEntity.setCalle(direccionDto.getCalle());
						direccionEntity.setNumero(direccionDto.getNumero());
						direccionEntity.setColonia(direccionDto.getColonia());
						direccionEntity.setCp(direccionDto.getCp());
						direccionEntity.setDelegacion(direccionDto.getDelegacion());
						direccionEntity.setEstado(direccionDto.getEstado());
						direccionEntity.setReferencia(direccionDto.getReferencia() != null ? direccionDto.getReferencia() : null);
						
						direccionEntity.setCliente(clienteBDD);
						clienteBDD.getDirecciones().add(direccionEntity);
					}
				}
			}

			clienteRepository.save(clienteBDD);
			
			response.setCode(MensajesAplicacion.MENSAJE_EXITO.getCodigo());
			response.setMessage(MensajesAplicacion.MENSAJE_EXITO.getMensaje());
			
			return response;
		} else {
			response.setCode(MensajesAplicacion.MENSAJE_FALLO.getCodigo());
			response.setMessage(MensajesAplicacion.MENSAJE_FALLO.getMensaje());
			response.setErrors(Arrays.asList("Cliente no encontrado"));
			
			response.setData(clienteDTO);
			
			return response;
		}
	}

	@Override
	@Transactional
	public RespuestaGeneral<?> deleteCustomer(long idCliente) throws SerialException, SQLException {
		log.info("Servicio clientes: DELETE cliente");
		
		RespuestaGeneral<ClienteDto> response = new RespuestaGeneral<ClienteDto>();
		
		if(clienteRepository.existsById(idCliente)) {
			ClienteEntity clienteBDD = clienteRepository.findById(idCliente).orElse(null);
			clienteRepository.delete(clienteBDD);
			
			response.setCode(MensajesAplicacion.MENSAJE_EXITO.getCodigo());
			response.setMessage(MensajesAplicacion.MENSAJE_EXITO.getMensaje());
			response.setData(null);
		} else {
			response.setCode(MensajesAplicacion.MENSAJE_FALLO.getCodigo());
			response.setMessage(MensajesAplicacion.MENSAJE_FALLO.getMensaje());
			response.setData(null);
		}
				
		return response;
	}
}
