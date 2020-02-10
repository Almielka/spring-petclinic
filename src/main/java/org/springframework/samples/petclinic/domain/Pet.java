package org.springframework.samples.petclinic.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Simple JavaBean domain object representing a Pet.
 * Pet has a name property {@code name}
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
@Table(name = "pet", indexes = {@Index(columnList = "name")})
public class Pet extends BaseNameEntity {

    @Column(name = "birthday_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Setter(AccessLevel.PRIVATE)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_type_id")
    private PetType petType;

    @Setter(AccessLevel.PRIVATE)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Visit> visitSet = new LinkedHashSet<>();

}
