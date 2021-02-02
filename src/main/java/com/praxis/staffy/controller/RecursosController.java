package com.praxis.staffy.controller;

public interface RecursosController {
	
	String RAIZ = "/";
	String RAIZ2 = "";
	
	String RAIZ_STAFFY = "/movil";
	String RAIZ_STAFFY_LOGIN = "/login";
	String PATH_USUARIOS_CHANGE_PASSWORD = "/login/cambiarPassword";
	
	String PATH_CLIENTES = "/clientes";
	String PATH_CLIENTES_FIND_BYID = "/clientes/{idCliente}";
	String PATH_CLIENTES_ADD = "/clientes";
	String PATH_CLIENTES_NOMBRE = "/clientes/filtro";
	
	String PATH_NOTIFICACIONES = "/notificaciones";
	
	String PATH_USUARIOS = "/usuarios";
	
}
