package com.praxis.staffy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.praxis.staffy.model.entidad.ClienteEntity;

public interface ClienteRepository extends CrudRepository<ClienteEntity, Long> {
	List<ClienteEntity> findByNombreContaining(String title);
}
