package org.springframework.samples.petclinic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Simple JavaBean domain object adds a name property {@code name} to {@code BaseIdEntity}.
 * Used as a base class for objects needing these properties.
 *
 * @author Anna S. Almielka
 */

@MappedSuperclass
@Getter
@Setter
public abstract class BaseNameEntity extends BaseIdEntity {

    @Column(nullable = false)
    @NotEmpty(message = "Name may not be empty")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
