package org.springframework.samples.petclinic.service.impl;

import org.springframework.samples.petclinic.domain.Pet;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.stereotype.Service;

/**
 * Extension of {@link GenericServiceImpl}
 * with explicitly defined the repository,
 * which is then called in the abstract constructor {@link GenericServiceImpl#GenericServiceImpl}
 * class {@code Pet}, repository {@code PetRepository}
 *
 * @author Anna S. Almielka
 */

@Service
public class PetServiceImpl extends GenericServiceImpl<Pet, PetRepository> {
    public PetServiceImpl(PetRepository repository) {
        super(repository);
    }
}
