package com.praxis.staffy.model.dto;

import java.io.Serializable;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.praxis.staffy.utilerias.GrupoValidaciones.ActualizarCliente;
import com.praxis.staffy.utilerias.GrupoValidaciones.CrearCliente;

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
public class ClienteDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull(groups = {ActualizarCliente.class}, message = "Campo idCliente no puede ser nulo en una actualizacion.")
	private Long idCliente;
	
	@NotBlank(groups = {CrearCliente.class, ActualizarCliente.class}, message = "Campo nombre no puede ser nulo.")
	@Size(groups = {CrearCliente.class, ActualizarCliente.class}, max = 50, message = "Campo nombre no puede ser mayor a 50 caracteres.")
	private String nombre;
	
	private String imagen;
	
	private long proyectosEspera;
	
	private long proyectosConcluidos;
	
	@Valid
	@NotEmpty(groups = {CrearCliente.class}, message = "Campo direcciones no puede ser nulo")
	private Set<DireccionDto> direcciones;

}
