package org.springframework.samples.petclinic.service;

import org.springframework.samples.petclinic.domain.BaseIdEntity;

import java.util.List;
import java.util.Optional;

/**
 * Generic Service interface with common CRUD methods
 *
 * @param <T> Class extends BaseIdEntity
 *
 * @author Anna S. Almielka
 */

public interface GenericService<T extends BaseIdEntity> {
    <S extends T> S saveAndFlush(S entity);
    Optional<T> findById(Integer id);
    List<T> findAll();
    void delete(T entity);
}
