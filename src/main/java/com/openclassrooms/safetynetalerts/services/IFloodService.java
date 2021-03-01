package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * L'interface IFloodService est le service permettant de recevoir les listes
 * des foyers couverts par les casernes entrées en paramètre, regroupant les
 * personnes par adresse avec leurs informations(nom, numéro de téléphone, âge
 * et leurs antécédents médicaux).
 * 
 * @author Dylan
 *
 */
public interface IFloodService {
    
    /**
     * La méthode findHomesServedByTheStation permet de recevoir les listes des
     * foyers couverts par les casernes indiquées en paramètre, regroupant les
     * personnes par adresse avec leurs informations.
     * 
     * @param station liste des numéros de caserne.
     * @return hashmap avec les listes des foyers regroupant les personnes par
     *         adresse.
     * @throws IOException    si une erreur est rencontrée lors de la lecture des
     *                        données.
     * @throws ParseException si une erreur est rencontrée lors de l'analyse des
     *                        dates de naissance.
     */
    public HashMap<String, Object> findHomesServedByTheStation (List<Integer> station)
	    throws IOException, ParseException;

}
