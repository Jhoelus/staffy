package com.praxis.staffy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.praxis.staffy.model.dto.RespuestaGeneral;
import com.praxis.staffy.service.notificaciones.NotificacionService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = RecursosController.RAIZ_STAFFY)
@Slf4j
@Service
@AllArgsConstructor
public class NotificacionController {
	
	private NotificacionService notificacionService;
	
	@GetMapping(path= RecursosController.PATH_NOTIFICACIONES)
	public ResponseEntity<RespuestaGeneral<?>>  obtenerNotificaciones() {
		log.info("Controller notificaciones: find all");
		return new ResponseEntity<RespuestaGeneral<?>>(notificacionService.findAllNotificaciones(), HttpStatus.OK);
	}
	
}
