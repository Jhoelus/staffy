package com.praxis.staffy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = {RecursosController.RAIZ, RecursosController.RAIZ2})
public class InfoController {
	
	@GetMapping
	public ResponseEntity<String> inicio() {
		return ok("Server up..!!! ");
	}
	
}
