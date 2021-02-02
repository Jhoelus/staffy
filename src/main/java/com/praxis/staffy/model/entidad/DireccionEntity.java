package com.praxis.staffy.model.entidad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Entity
@Table(name="direcciones")
public class DireccionEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long idDireccion;
	
	private String  calle;
	private String  numero;
	private String  colonia;
	private String  cp;
	private String  delegacion;
	private String  estado;
	private String  referencia;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_direccion_cliente_id"), name = "cliente_id", nullable = false)
	private ClienteEntity cliente;
	 	
	@Column(name="creation_date")
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;
}
