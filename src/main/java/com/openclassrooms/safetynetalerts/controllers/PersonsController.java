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
import com.openclassrooms.safetynetalerts.services.IPersonsService;

/**
 * La classe PersonsController permet de créer l'url person, dans laquel nous
 * pouvons récupérer la liste des personnes enregistrées,
 * ajouter/modifier/supprimer une personne.
 * 
 * @author Dylan
 *
 */
@RestController
@RequestMapping(value = "person")
public class PersonsController {

    @Autowired
    IPersonsService personsService;

    /**
     * Reçoit la liste des personnes enregistrées.
     * 
     * @return la liste des personnes enregistrées.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    @GetMapping(value = "/get")
    public List<PersonsModel> getPersonsList() throws IOException {
	return personsService.findAll();
    }

    /**
     * Ajout d'une personne.
     * 
     * @param newPerson les informations de la personne dans le body de la requête.
     * @return un message validant l'ajout de la personne avec le status created, ou
     *         le status noContent si l'ajout n'a pas abouti.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    @PostMapping(value = "/add")
    public ResponseEntity<?> addPerson(@RequestBody PersonsModel newPerson) throws IOException {
	var personAdded = personsService.saveNewPerson(newPerson);

	if (!personAdded) {
	    return ResponseEntity.noContent().build();
	} else {
	    return ResponseEntity.status(HttpStatus.CREATED)
		    .body("Person " + newPerson.getFirstName() + " " + newPerson.getLastName() + " added with success");
	}
    }

    /**
     * Mis à jour d'une personne.
     * 
     * @param person les nouvelles informations de la personne dans le body de la
     *               requête.
     * @return un message validant la mis à jour avec le status ok, ou le status
     *         notFound si la mis à jour n'a pas aboutie.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    @PutMapping(value = "/update")
    public ResponseEntity<?> updatePerson(@RequestBody PersonsModel person) throws IOException {
	var personUpdated = personsService.updatePerson(person);
	if (!personUpdated) {
	    return ResponseEntity.notFound().build();
	} else {
	    return ResponseEntity.status(HttpStatus.OK)
		    .body(person.getFirstName() + " " + person.getLastName() + " informations updated with success");
	}
    }

    /**
     * Suppression d'une personne.
     * 
     * @param firstName prénom de la personne.
     * @param lastName  nom de la personne.
     * @return un message validant la suppression avec le status ok, ou le status
     *         notFound si la suppression n'a pas aboutie.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deletePerson(@RequestParam(value = "firstName") String firstName,
	    @RequestParam(value = "lastName") String lastName) throws IOException {
	var personDeleted = personsService.deletePerson(firstName, lastName);
	if (!personDeleted) {
	    return ResponseEntity.notFound().build();
	} else {
	    return ResponseEntity.status(HttpStatus.OK).body(firstName + " " + lastName + " deleted with succes");
	}
    }
}
