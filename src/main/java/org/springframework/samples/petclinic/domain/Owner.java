package org.springframework.samples.petclinic.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Simple JavaBean domain object representing an Owner.
 * Owner has a firstName property {@code firstName} and a lastName property {@code lastName}
 * than extends class with these properties {@code BasePersonEntity}.
 *
 * @author Anna S. Almielka
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Component
@Table(name = "owner", indexes = {@Index(columnList = "last_name")})
public class Owner extends BasePersonEntity {

    @Column
    @NotEmpty(message = "City may not be empty")
    @Size(min = 2, max = 16, message = "City must be between 2 and 16 characters long")
    private String city;

    @Column
    @NotEmpty(message = "Address may not be empty")
    @Size(min = 2, max = 32, message = "Address must be between 6 and 32 characters long")
    private String address;

    @Column
    @NotEmpty(message = "Phone number may not be empty")
    @Digits(fraction = 0, integer = 12, message = "Need digits and no more than 12")
    private String phone;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Pet> petSet = new LinkedHashSet<>();
}
