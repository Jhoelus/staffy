package com.praxis.staffy.model.entidad;

import java.io.Serializable;
import java.sql.Clob;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name="clientes")
public class ClienteEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long idCliente;
	
	private String nombre;
	
	@Column(name="proyectos_espera")
	private long proyectosEspera;
	
	@Column(name="proyectos_concluidos")
	private long proyectosConcluidos;
	
	private Clob imagen;
	
	@OneToMany(
			mappedBy = "cliente",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
    private List<DireccionEntity> direcciones;
		
    @Temporal(TemporalType.DATE)
	@Column(name="creation_date")
	private Date fechaCreacion;
		
}
