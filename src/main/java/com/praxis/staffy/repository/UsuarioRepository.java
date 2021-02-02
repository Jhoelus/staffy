package com.praxis.staffy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.praxis.staffy.model.entidad.UsuarioEntity;

public interface UsuarioRepository extends CrudRepository<UsuarioEntity, Long> {
	
	List<UsuarioEntity> findByUsuario(String correo);
}
