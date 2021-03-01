package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.services.IFloodService;

/**
 * La classe FloodController permet de créer l'url flood/stations, qui reçoit en
 * paramètre un ou plusieurs numéro de station, et qui renvoit tous les foyers
 * desservis par celles-ci, regroupant les personnes par adresse avec leurs
 * informations (nom, numéro de téléphone, âge et antécédents médicaux).
 * 
 * @author Dylan
 *
 */
@RestController
public class FloodController {

    @Autowired
    IFloodService floodService;

    /**
     * Reçoit la liste des foyers avec les informations des personnes couvertes par
     * les numéros de stations indiqués en paramètre.
     * 
     * @param station le ou les numéros de station que l'on souhaite vérifier.
     * @return hashmap incluant les numéros de stations dans lesquels il y a la liste des
     *         foyers couverts, qui inclus les informations des personnes présentes
     *         dans chaque foyer.
     * @throws IOException    si une erreur est rencontrée lors de la lecture des
     *                        données.
     * @throws ParseException si une erreur est rencontrée lors de l'analyse des
     *                        dates de naissances.
     */
    @GetMapping(value = "flood/stations")
    public HashMap<String, Object> getHomesServedByTheStation(@RequestParam(value = "stations") List<Integer> station)
	    throws IOException, ParseException {
	return floodService.findHomesServedByTheStation(station);
    }

}
