package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.domain.Pet;
import org.springframework.stereotype.Repository;

/**
 * Extension of {@link GenericRepository} with concrete generic Pet
 *
 * @author Anna S. Almielka
 */

@Repository
public interface PetRepository extends GenericRepository<Pet> {
}

