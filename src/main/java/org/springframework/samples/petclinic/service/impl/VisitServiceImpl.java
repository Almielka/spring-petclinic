package org.springframework.samples.petclinic.service.impl;

import org.springframework.samples.petclinic.domain.Visit;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;

/**
 * Extension of {@link GenericServiceImpl}
 * with explicitly defined the repository,
 * which is then called in the abstract constructor {@link GenericServiceImpl#GenericServiceImpl}
 * class {@code Visit}, repository {@code VisitRepository}
 *
 * @author Anna S. Almielka
 */

@Service
public class VisitServiceImpl extends GenericServiceImpl<Visit, VisitRepository> {
    public VisitServiceImpl(VisitRepository repository) {
        super(repository);
    }
}
