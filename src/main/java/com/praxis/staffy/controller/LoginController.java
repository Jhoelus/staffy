package com.praxis.staffy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.praxis.staffy.model.dto.RespuestaGeneral;
import com.praxis.staffy.model.dto.UsuarioDto;
import com.praxis.staffy.service.usuarios.UsuarioService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path= RecursosController.RAIZ_STAFFY)
@Slf4j
@Service
@AllArgsConstructor
public class LoginController {
	
	UsuarioService usuarioService;
	
//	@GetMapping
//	public ResponseEntity<String> inicio(@Validated @RequestBody UsuarioDto user) {
//		log.info("Login Exitoso...!!!");
//		return ok("Exito..!!");
//	}
	
	@PutMapping(path= RecursosController.PATH_USUARIOS_CHANGE_PASSWORD)
	public ResponseEntity<RespuestaGeneral<?>>  cambiarPassword(@Validated @RequestBody UsuarioDto user) {
		log.info("Controller usuarios: cambiar password");
		return new ResponseEntity<RespuestaGeneral<?>>(usuarioService.cambiarPassword(user), HttpStatus.OK);
	}
}
