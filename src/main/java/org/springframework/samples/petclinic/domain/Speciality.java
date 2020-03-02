package org.springframework.samples.petclinic.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple JavaBean domain object representing a Speciality.
 * Speciality has a name property {@code name}
 * than extends class with this property {@code BaseNameEntity}.
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
@Table(name = "speciality", indexes = {@Index(columnList = "name")})
public class Speciality extends BaseNameEntity {

    @ManyToMany(mappedBy = "specialitySet")
    private Set<Vet> vetSet = new HashSet<>();

}
