package org.springframework.samples.petclinic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.domain.BaseIdEntity;
import org.springframework.samples.petclinic.repository.GenericRepository;
import org.springframework.samples.petclinic.service.GenericService;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link GenericService}
 * includes parameterized constructor for the future repository
 * and with override all methods
 *
 * @param <T> Class extends BaseIdEntity
 * @param <R> Repository extends GenericRepository<T>
 *
 * @author Anna S. Almielka
 */

public abstract class GenericServiceImpl<T extends BaseIdEntity, R extends GenericRepository<T>> implements GenericService<T> {

    protected final R repository;

    @Autowired
    public GenericServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public Optional<T> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }
}
