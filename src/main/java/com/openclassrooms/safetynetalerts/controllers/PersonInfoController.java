package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.services.IPersonInfoService;

/**
 * La classe PersonInfoController permet de créer l'url personInfo, qui reçoit
 * en paramètre le prenom et nom de la personne auquel on souhaite vérifier ses
 * informations, ce qui nous renvoit le nom, l'adresse, l'âge, l'E-mail et les
 * antécédents médicaux.
 * 
 * @author Dylan
 *
 */
@RestController
public class PersonInfoController {

    @Autowired
    IPersonInfoService personInfoService;

    /**
     * Reçoit les informations de la personne, si deux personnes ont le même prénom
     * et nom, alors les informations de celles-ci s'affichent.
     * 
     * @param firstName prénom de la personne.
     * @param lastName  nom de la personne.
     * @return hashmap avec les informations(nom, adresse, âge, E-mail et
     *         antécédents médicaux) de la personne.
     * @throws IOException    si une erreur est rencontrée lors de la lecture des
     *                        données.
     * @throws ParseException si une erreur est renontrée lors de l'analyse des
     *                        dates de naissances.
     */
    @GetMapping(value = "personInfo")
    public HashMap<String, Object> getPersonInfoByName(@RequestParam(value = "firstName") String firstName,
	    @RequestParam(value = "lastName") String lastName) throws IOException, ParseException {

	return personInfoService.findPersonInfoByName(firstName, lastName);
    }

}
