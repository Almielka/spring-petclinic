package org.springframework.samples.petclinic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Simple JavaBean domain object adds a firstName property {@code firstName} to {@code BaseIdEntity}.
 * and a lastName property {@code lastName} to {@code BaseIdEntity}.
 * Used as a base class for objects needing these properties.
 *
 * @author Anna S. Almielka
 */

@MappedSuperclass
@Getter
@Setter
public class BasePersonEntity extends BaseIdEntity {

    @Column(name = "first_name")
    @NotEmpty(message = "Name may not be empty")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last Name may not be empty")
    @Size(min = 2, max = 32, message = "Last Name must be between 2 and 32 characters long")
    private String lastName;

    @Override
    public String toString() {
        return lastName;
    }
}
