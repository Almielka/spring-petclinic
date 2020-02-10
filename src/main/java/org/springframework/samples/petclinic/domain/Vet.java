package org.springframework.samples.petclinic.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Simple JavaBean domain object representing a Vet.
 * Vet has a firstName property {@code firstName} and a lastName property {@code lastName}
 * than extends class with these properties {@code BasePersonEntity}.
 *
 * @author Anna S. Almielka
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@Component
@Table(name = "vet", indexes = {@Index(columnList = "last_name")})
public class Vet extends BasePersonEntity {

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "vet_speciality",
        joinColumns = @JoinColumn(name = "vet_id"),
        inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private Set<Speciality> specialitySet = new LinkedHashSet<>();

}
