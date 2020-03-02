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

import org.springframework.samples.petclinic.domain.Owner;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 *
 * @author Anna S. Almielka
 */

@Controller
class OwnerController {
    /**
     * Added another constants
     */
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    private static final String VIEWS_FIND_OWNERS = "owners/findOwners";
    private static final String VIEWS_OWNERS_LIST = "owners/ownersList";
    private static final String VIEWS_OWNERS_DETAIL = "owners/ownerDetails";
    private static final String REDIRECT_OWNERS = "redirect:/owners";

    private OwnerRepository ownerRepository;

    public OwnerController(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
        dataBinder.setDisallowedFields("createdAt");
    }

    @GetMapping("/owners/find")
    public String initFindForm(Map<String, Object> model) {
        model.put("owner", new Owner());
        return VIEWS_FIND_OWNERS;
    }

    @PostMapping("/owners/find")
    public String processFindForm(Owner owner, BindingResult result, Map<String, Object> model) {

        if (owner.getLastName().equals("")) {
            result.rejectValue("lastName", "notFound", "not found");
            return VIEWS_FIND_OWNERS;
        } else {
            // find owners by last name
            Collection<Owner> ownerCollection = ownerRepository.findByLastNameContaining(owner.getLastName());
            if (ownerCollection.isEmpty()) {
                // no owners found
                result.rejectValue("lastName", "notFound", "not found");
                return VIEWS_FIND_OWNERS;
            } else if (ownerCollection.size() == 1) {
                // 1 owner found
                owner = ownerCollection.iterator().next();
                return REDIRECT_OWNERS + "/" + owner.getId();
            } else {
                // multiple owners found
                model.put("selections", ownerCollection);
                return VIEWS_OWNERS_LIST;
            }
        }
    }

    /**
     * Added view Collection of Owners, own url /owners
     *
     * @return a ModelAndView with the model attributes for the view
     */
    @GetMapping("/owners")
    public ModelAndView showAllOwners() {
        ModelAndView mav = new ModelAndView(VIEWS_OWNERS_LIST);
        Collection<Owner> ownerCollection = ownerRepository.findAll();
        mav.addObject("selections", ownerCollection);
        return mav;
    }

    @GetMapping("/owners/new")
    public String initCreationForm(Map<String, Object> model) {
        Owner owner = new Owner();
        model.put("owner", owner);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/owners/new")
    public String processCreationForm(@Valid Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            this.ownerRepository.saveAndFlush(owner);
            return REDIRECT_OWNERS + "/" + owner.getId();
        }
    }

    /**
     * Added Optional with redirecting, if notPresent to url /owners
     *
     * @param ownerId the ID of the Owner to display
     * @return a ModelAndView with the model attributes for the view
     */
    @GetMapping("/owners/{ownerId}/edit")
    public ModelAndView initUpdateOwnerForm(@PathVariable("ownerId") Integer ownerId) {
        Optional<Owner> owner = ownerRepository.findById(ownerId);
        if (owner.isPresent()) {
            ModelAndView mav = new ModelAndView(VIEWS_OWNER_CREATE_OR_UPDATE_FORM);
            mav.addObject("owner", owner.get());
            return mav;
        } else {
            return new ModelAndView(REDIRECT_OWNERS);
        }
    }

    @PostMapping("/owners/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable("ownerId") Integer ownerId) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            owner.setId(ownerId);
            this.ownerRepository.saveAndFlush(owner);
            return REDIRECT_OWNERS + "/{ownerId}";
        }
    }

    /**
     * Custom handler for displaying an owner.
     * Added Optional with redirecting, if notPresent to url /owners
     * Re-organize Collection of Pets and Visits:
     * moved to {@link templates/owners/ownerDetails} {@code owner.getPetSet()}{@code pet.getVisitSet()}
     *
     * @param ownerId the ID of the owner to display
     * @return a ModelMap with the model attributes for the view
     */
    @GetMapping("/owners/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Integer ownerId) {

        Optional<Owner> owner = ownerRepository.findById(ownerId);
        if (owner.isPresent()) {
            ModelAndView mav = new ModelAndView(VIEWS_OWNERS_DETAIL);
            mav.addObject("owner", owner.get());
            return mav;
        } else {
            return new ModelAndView(REDIRECT_OWNERS);
        }
    }
}
