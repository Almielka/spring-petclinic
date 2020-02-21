package org.springframework.samples.petclinic.service;

import org.springframework.samples.petclinic.domain.Owner;

import java.util.Collection;

/**
 * OwnerService interface includes additional methods only for Owner
 *
 * @author Anna S. Almielka
 */

public interface OwnerService {
    Collection<Owner> findByLastName(String lastName);
}
