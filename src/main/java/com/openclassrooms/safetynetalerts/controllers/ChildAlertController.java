package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.services.IChildAlertService;

/**
 * La classe ChildAlertController permet de créer l'url childAlert qui reçoit en
 * paramètre une adresse, ceux-ci va nous renvoyer une hasmap avec les listes
 * des enfants et adultes présents à cette adresse.
 * 
 * @author Dylan
 *
 */

@RestController
public class ChildAlertController {

    @Autowired
    IChildAlertService childAlertService;

    /**
     * Reçoit la liste des enfants et adultes présents à l'adresse indiquée.
     * 
     * @param address l'adresse que l'on veut vérifiée.
     * @return hasmap avec deux listes (enfants et adultes).
     * @throws IOException    si une erreur est rencontrée lors de la lecture des
     *                        données.
     * @throws ParseException si une erreur est rencontrée lors de la l'analyse des
     *                        dates de naissances.
     */
    @GetMapping(value = "childAlert")
    public HashMap<String, Object> getListChildAtTheAddress(@RequestParam(value = "address") String address)
	    throws IOException, ParseException {
	return childAlertService.findChildAtAddress(address);
    }
}
