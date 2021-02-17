package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.services.impl.PersonsServiceImpl;

@RestController
@RequestMapping(value = "person")
public class PersonsController {

    @Autowired
    PersonsServiceImpl personsServiceImpl;

    @GetMapping(value = "/get")
    public List<PersonsModel> getPersonsList() throws IOException {
	return personsServiceImpl.findAll();
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addPerson(@RequestBody PersonsModel newPerson) throws IOException {
	var personAdded = personsServiceImpl.saveNewPerson(newPerson);

	if (!personAdded) {
	    return ResponseEntity.noContent().build();
	} else {
	    return ResponseEntity.status(HttpStatus.CREATED)
		    .body("Person " + newPerson.getFirstName() + " " + newPerson.getLastName() + " added with success");
	}
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updatePerson(@RequestBody PersonsModel person) throws IOException {
	var personUpdated = personsServiceImpl.updatePerson(person);
	if (!personUpdated) {
	    return ResponseEntity.notFound().build();
	} else {
	    return ResponseEntity.status(HttpStatus.OK)
		    .body(person.getFirstName() + " " + person.getLastName() + " informations updated with success");
	}
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deletePerson(@RequestParam(value = "firstName") String firstName,
	    @RequestParam(value = "lastName") String lastName) throws IOException {
	var personDeleted = personsServiceImpl.deletePerson(firstName, lastName);
	if (!personDeleted) {
	    return ResponseEntity.notFound().build();
	} else {
	    return ResponseEntity.status(HttpStatus.OK)
		    .body(firstName + " " + lastName + " deleted with succes");
	}
    }
}
