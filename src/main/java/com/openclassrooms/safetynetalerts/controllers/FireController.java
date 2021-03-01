package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.services.IFireService;

/**
 * La classe FireController permet de créer l'url fire, qui reçoit comme
 * paramètre l'adresse, ceux-ci va nous renvoyer la liste des personnes
 * présentes à cette adresse avec leurs informations (nom, numéro de téléphone,
 * âge et antécédents médicaux), ainsi que le numéro de la station qui couvre
 * cette zone.
 * 
 * @author Dylan
 *
 */
@RestController
public class FireController {

    @Autowired
    IFireService fireService;

    /**
     * Reçoit la liste des personnes présentes à cette adresse avec leurs
     * informations (nom, numéro de téléphone, âge et antécédents médicaux) ainsi
     * que le numéro de la station couvrant cette zone.
     * 
     * @param address l'adresse que l'ont souhaite vérifier.
     * @return hasmap avec la liste des personnes et le numéro de station.
     * @throws IOException    si une erreur est rencontrée lors de la lecture des
     *                        données.
     * @throws ParseException si une erreur est rencontrée lors de l'analyse des
     *                        dates de naissances.
     */
    @GetMapping(value = "fire")
    public HashMap<String, Object> getPersonsAndFireStationAtTheAddress(@RequestParam(value = "address") String address)
	    throws IOException, ParseException {
	return fireService.findPersonsAndFireStationAtTheAddress(address);
    }
}
