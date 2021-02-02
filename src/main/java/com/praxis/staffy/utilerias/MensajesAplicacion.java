package com.praxis.staffy.utilerias;

/**
 * 
 */

public enum MensajesAplicacion {
	
	MENSAJE_EXITO(0,   "Operacion realizada correctamente."),
	MENSAJE_FALLO(1, "Operacion Incorrecta."),
	MENSAJE_ERROR(2, "Error inesperado, consulte administrador");
	
	Integer codigo;
	String mensaje;
	
	MensajesAplicacion(Integer codigo, String mensaje) {
		this.codigo = codigo;
		this.mensaje = mensaje;
	}
	
	public Integer getCodigo() {
		return this.codigo;
	}
	
	public String getMensaje() {
		return this.mensaje;
	}

}
