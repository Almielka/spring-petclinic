package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.domain.Vet;
import org.springframework.stereotype.Repository;

/**
 * Extension of {@link GenericRepository} with concrete generic Vet
 *
 * @author Anna S. Almielka
 */

@Repository
public interface VetRepository extends GenericRepository<Vet> {
}
