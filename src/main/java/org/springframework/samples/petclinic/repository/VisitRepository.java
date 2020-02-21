package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.domain.Visit;
import org.springframework.stereotype.Repository;

/**
 * Extension of {@link GenericRepository} with concrete generic Visit
 *
 * @author Anna S. Almielka
 */

@Repository
public interface VisitRepository extends GenericRepository<Visit> {
}
