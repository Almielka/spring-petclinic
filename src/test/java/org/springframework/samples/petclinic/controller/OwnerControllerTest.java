/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.controller;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.samples.petclinic.domain.Owner;
import org.springframework.samples.petclinic.domain.Pet;
import org.springframework.samples.petclinic.domain.PetType;
import org.springframework.samples.petclinic.domain.Visit;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.assertj.core.util.Lists;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 * @author Anna S. Almielka
 */
@WebMvcTest(OwnerController.class)
class OwnerControllerTests {

    private static final int TEST_OWNER_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerRepository owners;

    @MockBean
    private VisitRepository visits;

    private Owner george;


    @BeforeEach
    void setup() {
        george = new Owner();
        george.setId(TEST_OWNER_ID);
        george.setFirstName("George");
        george.setLastName("Franklin");
        george.setAddress("110 W. Liberty St.");
        george.setCity("Madison");
        george.setPhone("6085551023");
        george.setCreatedAt(LocalDate.of(2015,10,7));

        PetType cat = new PetType();
        cat.setName("cat");
        cat.setId(1);

        Pet leo = new Pet();
        leo.setId(1);
        leo.setName("Leo");
        leo.setBirthday(LocalDate.of(2015, 9, 7));
        leo.setPetType(cat);
        leo.setOwner(george);

        Visit visit1 = new Visit();
        Visit visit2 = new Visit();
        visit1.setId(1);
        visit2.setId(2);
        visit1.setDate(LocalDate.of(2016,3,4));
        visit2.setDate(LocalDate.of(2019,11,13));
        visit1.setPet(leo);
        visit2.setPet(leo);
        Set<Visit> visitSet = Stream.of(visit1, visit2)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        given(this.owners.findById(TEST_OWNER_ID)).willReturn(Optional.ofNullable(george));

        george.setPetSet(Collections.singleton(leo));
        cat.setPetSet(Collections.singleton(leo));
        leo.setVisitSet(Collections.unmodifiableSet(visitSet));

    }

    @Test
    void testInitFindForm() throws Exception {
        mockMvc.perform(get("/owners/find")).andExpect(status().isOk()).andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void testProcessFindFormIsEmpty() throws Exception {
        mockMvc.perform(post("/owners/find").param("lastName", "")).andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("owner", "lastName"))
                .andExpect(model().attributeHasFieldErrorCode("owner", "lastName", "notFound"))
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void testProcessFindFormNoOwnersFound() throws Exception {
        mockMvc.perform(post("/owners/find").param("lastName", "Unknown Surname")).andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("owner", "lastName"))
                .andExpect(model().attributeHasFieldErrorCode("owner", "lastName", "notFound"))
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void testProcessFindFormByLastName() throws Exception {
        given(this.owners.findByLastNameContaining(george.getLastName())).willReturn(Lists.newArrayList(george));
        mockMvc.perform(post("/owners/find").param("lastName", "Franklin")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + TEST_OWNER_ID));
    }

    @Test
    void testShowAllOwnersSuccess() throws Exception {
        mockMvc.perform(get("/owners")).andExpect(status().isOk()).andExpect(view().name("owners/ownersList"));
    }

    @Test
    void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/owners/new")).andExpect(status().isOk()).andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    void testProcessCreationFormSuccess() throws Exception {
        mockMvc.perform(post("/owners/new").param("firstName", "Joe").param("lastName", "Bloggs")
                .param("address", "123 Caramel Street").param("city", "London").param("phone", "01316761638"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testInitUpdateOwnerForm() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}/edit", TEST_OWNER_ID)).andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attribute("owner", hasProperty("lastName", is("Franklin"))))
                .andExpect(model().attribute("owner", hasProperty("firstName", is("George"))))
                .andExpect(model().attribute("owner", hasProperty("address", is("110 W. Liberty St."))))
                .andExpect(model().attribute("owner", hasProperty("city", is("Madison"))))
                .andExpect(model().attribute("owner", hasProperty("phone", is("6085551023"))))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    void testProcessUpdateOwnerFormSuccess() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID).param("firstName", "Joe")
                .param("lastName", "Bloggs").param("address", "123 Caramel Street").param("city", "London")
                .param("phone", "01616291589")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    void testProcessCreationFormHasErrors() throws Exception {
        mockMvc.perform(
                post("/owners/new").param("firstName", "Joe").param("lastName", "Bloggs").param("city", "London"))
                .andExpect(status().isOk()).andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner", "address"))
                .andExpect(model().attributeHasFieldErrors("owner", "phone"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    void testProcessUpdateOwnerFormHasErrors() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID).param("firstName", "Joe")
                .param("lastName", "Bloggs").param("city", "London")).andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner", "address"))
                .andExpect(model().attributeHasFieldErrors("owner", "phone"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    void testShowOwner() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}", TEST_OWNER_ID)).andExpect(status().isOk())
                .andExpect(model().attribute("owner", hasProperty("lastName", is("Franklin"))))
                .andExpect(model().attribute("owner", hasProperty("firstName", is("George"))))
                .andExpect(model().attribute("owner", hasProperty("address", is("110 W. Liberty St."))))
                .andExpect(model().attribute("owner", hasProperty("city", is("Madison"))))
                .andExpect(model().attribute("owner", hasProperty("phone", is("6085551023"))))
                .andExpect(model().attribute("owner", hasProperty("petSet", not(empty()))))
                .andExpect(model().attribute("owner", hasProperty("petSet", new BaseMatcher<Set<Pet>>() {

            @Override
            public boolean matches(Object item) {
                @SuppressWarnings("unchecked")
                Set<Pet> pets = (Set<Pet>) item;
                Pet pet = pets.stream().findFirst().get();
                return !pet.getVisitSet().isEmpty();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Leo did not have any visits");
            }
        }))).andExpect(view().name("owners/ownerDetails"));
    }

}
