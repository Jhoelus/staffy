package com.praxis.staffy.service.notificaciones.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.praxis.staffy.model.dto.NotificacionDto;
import com.praxis.staffy.model.dto.RespuestaGeneral;
import com.praxis.staffy.model.entidad.NotificacionEntity;
import com.praxis.staffy.repository.NotificacionRepository;
import com.praxis.staffy.service.notificaciones.NotificacionService;
import com.praxis.staffy.utilerias.MensajesAplicacion;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {
	
	
	private NotificacionRepository notificacionRepository;
	private ModelMapper mapper;
	
	@Override
	public RespuestaGeneral<?> findAllNotificaciones() {
		log.info("Service notificaciones: find all");
		
		RespuestaGeneral<List<NotificacionDto>> response = new RespuestaGeneral<List<NotificacionDto>>();
		List<NotificacionDto> notificacionDTO = new ArrayList<NotificacionDto>();
		
		List<NotificacionEntity> notificacionesEntity =  (List<NotificacionEntity>) notificacionRepository.findAll();
		
		notificacionesEntity.forEach((NotificacionEntity notificacion) -> {
			NotificacionDto clienteDTO = mapper.map(notificacion, NotificacionDto.class);
			notificacionDTO.add(clienteDTO);
		});
		
		response.setCode(MensajesAplicacion.MENSAJE_EXITO.getCodigo());
		response.setMessage(MensajesAplicacion.MENSAJE_EXITO.getMensaje());
		response.setData(notificacionDTO);
		
		return response;
	}

}
