package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.services.IPhoneAlertService;

/**
 * La classe PhoneAlertController permet de créer l'url phoneAlert, qui reçoit
 * en paramètre le numéro d'une station, pour avoir la liste des numéros de
 * téléphone des personnes couvertes par celle-ci.
 * 
 * @author Dylan
 *
 */
@RestController
public class PhoneAlertController {

    @Autowired
    IPhoneAlertService phoneAlertService;

    /**
     * Reçoit la liste des numéros de téléphone des personnes couvertes par la
     * station entrée en paramètre.
     * 
     * @param station numéro de la station.
     * @return hashmap avec les numéros de téléphone des personnes couvertes.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    @GetMapping(value = "phoneAlert")
    public HashMap<String, Object> getListPhoneNumbersPersonFindByTheStationNumber(
	    @RequestParam(value = "firestation") int station) throws IOException {

	return phoneAlertService.findPhoneNumberPersonByTheStationNumber(station);
    }

}
