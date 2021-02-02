package com.praxis.staffy.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class UsuarioDto implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long idUsuario;
	
	@NotBlank(message = "El campo usuario no puede ser nulo")
	private String usuario;
	
	@NotBlank(message = "El campo Contrasena no puede ser nulo")
	private String contrasena;
	
	private String rol;
	
}
