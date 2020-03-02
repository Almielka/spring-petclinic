package org.springframework.samples.petclinic.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 *  Simple JavaBean domain object with an id property {@code id}.
 *  Used as a base class for objects needing this property.
 *
 * @author Anna S. Almielka
 */

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public abstract class BaseIdEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public boolean isNew() {
        return this.id == null;
    }
}
