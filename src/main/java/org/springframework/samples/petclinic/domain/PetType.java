package org.springframework.samples.petclinic.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Simple JavaBean domain object representing a PetType.
 * PetType has a name property {@code name}
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
@Table(name = "pet_type", indexes = {@Index(columnList = "name")})
public class PetType extends BaseNameEntity {

    @OneToMany(mappedBy = "petType")
    private Set<Pet> petSet = new LinkedHashSet<>();

}
