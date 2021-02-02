package com.praxis.staffy.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
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
public class DireccionDto implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull(groups = {ActualizarCliente.class}, message = "Campo idDireccion no puede ser nulo en una actualizacion")
	private Long idDireccion;
	
	@NotBlank(groups = {CrearCliente.class, ActualizarCliente.class}, message = "Campo calle no puede ser nulo")
	@Size(groups = {CrearCliente.class, ActualizarCliente.class}, max = 20, message = "Campo calle no puede ser mayor a 20 caracteres")
	@NotNull(groups = {CrearCliente.class, ActualizarCliente.class}, message = "Campo calle no puede ser nulo")
	private String  calle;
	
	@NotBlank(groups = {CrearCliente.class, ActualizarCliente.class}, message = "Campo numero no puede ser nulo")
	@Size(groups = {CrearCliente.class, ActualizarCliente.class}, max = 20, message = "Campo numero no puede ser mayor a 20 caracteres")
	@NotNull(groups = {CrearCliente.class, ActualizarCliente.class}, message = "Campo numero no puede ser nulo")
	private String  numero;
	
	@NotBlank(groups = {CrearCliente.class, ActualizarCliente.class}, message = "Campo colonia no puede ser nulo")
	@Size(groups = {CrearCliente.class, ActualizarCliente.class}, max = 30, message = "Campo colonia no puede ser mayor a 30 caracteres")
	@NotNull(groups = {CrearCliente.class, ActualizarCliente.class}, message = "Campo colonia no puede ser nulo")
	private String  colonia;
	
	@NotBlank(groups = {CrearCliente.class, ActualizarCliente.class}, message = "Campo codigo postal no puede ser nulo")
	@Size(groups = {CrearCliente.class, ActualizarCliente.class}, max = 5, min = 5, message = "Campo codigo postal debe ser igual a 5 caracteres")
	private String  cp;
	
	@NotBlank(groups = {CrearCliente.class, ActualizarCliente.class}, message = "Campo delegacion no puede ser nulo")
	@Size(groups = {CrearCliente.class, ActualizarCliente.class}, max = 30, message = "Campo delegacion no puede ser mayor a 30 caracteres")
	private String  delegacion;
	
	@NotBlank(groups = {CrearCliente.class, ActualizarCliente.class}, message = "Campo estado no puede ser nulo")
	@Size(groups = {CrearCliente.class, ActualizarCliente.class}, max = 30, message = "Campo estado no puede ser mayor a 30 caracteres")
	private String  estado;
	
	@Size(groups = {CrearCliente.class, ActualizarCliente.class}, max = 50, message = "Campo referencia no puede ser mayor a 50 caracteres")
	private String  referencia;
}
