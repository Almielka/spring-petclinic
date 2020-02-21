package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.samples.petclinic.domain.BaseIdEntity;

import java.util.List;
import java.util.Optional;

/**
 * Generic Repository interface with common CRUD methods
 * @param <T> the entity class related to this Repository
 *
 * @author Anna S. Almielka
 */

@NoRepositoryBean
public interface GenericRepository<T extends BaseIdEntity> extends JpaRepository<T, Integer> {
    <S extends T> S saveAndFlush(S entity);
    Optional<T> findById(Integer id);
    List<T> findAll();
    void delete(T entity);
}
