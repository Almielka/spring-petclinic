package org.springframework.samples.petclinic.service.impl;

import org.springframework.samples.petclinic.domain.Owner;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Extension of {@link GenericServiceImpl} and implementation of {@link OwnerService}
 * with explicitly defined the repository,
 * which is then called in the abstract constructor {@link GenericServiceImpl#GenericServiceImpl}
 * class {@code Owner}, repository {@code OwnerRepository}.
 * Also includes override additional methods only for Owner
 *
 * @author Anna S. Almielka
 */

@Service
public class OwnerServiceImpl extends GenericServiceImpl<Owner, OwnerRepository> implements OwnerService {

    public OwnerServiceImpl(OwnerRepository repository) {
        super(repository);
    }

    @Override
    public Collection<Owner> findByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }

}
