package com.praxis.staffy.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.praxis.staffy.service.usuarios.UsuarioService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = RecursosController.RAIZ_STAFFY)
//@Slf4j
@Service
@AllArgsConstructor
public class UsuarioController {
	
	UsuarioService usuarioService;

//	@PutMapping(path= RecursosController.PATH_USUARIOS_CHANGE_PASSWORD)
//	public ResponseEntity<RespuestaGeneral<?>>  agregarCliente(@Validated @RequestBody UsuarioDto user) {
//		log.info("Controller usuarios: cambiar password");
//		return new ResponseEntity<RespuestaGeneral<?>>(usuarioService.cambiarPassword(user), HttpStatus.OK);
//	}
}
