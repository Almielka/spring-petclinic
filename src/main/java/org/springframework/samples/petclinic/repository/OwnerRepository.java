package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.domain.Owner;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Extension of {@link GenericRepository} with concrete generic Owner
 * includes additional methods only for Owner
 *
 * @author Anna S. Almielka
 */


@Repository
public interface OwnerRepository extends GenericRepository<Owner> {
    /**
     * Retrieve {@link Owner}s from the data store by last name, returning all owners
     * whose last name <i>containing</i> with the given name.
     * @param lastName Value to search for
     * @return a Collection of matching {@link Owner}s (or an empty Collection if none
     * found)
     */
    @Query("SELECT DISTINCT o FROM Owner o WHERE o.lastName LIKE %:lastName%")
    @Transactional(readOnly = true)
    Collection<Owner> findByLastNameContaining(@Param("lastName") String lastName);
}
