package org.springframework.samples.petclinic.service.impl;

import org.springframework.samples.petclinic.domain.Vet;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.stereotype.Service;

/**
 * Extension of {@link GenericServiceImpl}
 * with explicitly defined the repository,
 * which is then called in the abstract constructor {@link GenericServiceImpl#GenericServiceImpl}
 * class {@code Vet}, repository {@code VetRepository}
 *
 * @author Anna S. Almielka
 */

@Service
public class VetServiceImpl extends GenericServiceImpl<Vet, VetRepository> {
    public VetServiceImpl(VetRepository repository) {
        super(repository);
    }
}
