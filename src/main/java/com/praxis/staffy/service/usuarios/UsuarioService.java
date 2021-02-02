package com.praxis.staffy.service.usuarios;

import com.praxis.staffy.model.dto.RespuestaGeneral;
import com.praxis.staffy.model.dto.UsuarioDto;

public interface UsuarioService {
	
	RespuestaGeneral<?> cambiarPassword(UsuarioDto user);
}
