package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.domain.Owner;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Extension of {@link GenericRepository} with concrete generic Owner
 * includes additional methods only for Owner
 *
 * @author Anna S. Almielka
 */


@Repository
public interface OwnerRepository extends GenericRepository<Owner> {
    Collection<Owner> findByLastName(String lastName);
}
