package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

/**
 * L'interface IFireService est le service qui permet de renvoyer une liste des
 * personnes habitant à l'adresse indiquée en paramètre avec leurs
 * informations(nom, numéros de téléphone, âge et antécédents médicaux) ainsi
 * que le numéro de la caserne les desservant.
 * 
 * @author Dylan
 *
 */
public interface IFireService {

    /**
     * La méthode findPersonsAndFireStationAtTheAddress vérifie les personnes
     * présentes à l'adresse indiquée en paramètre, et le numéro de la caserne qui y
     * intervient.
     * 
     * @param address l'adresse que l'on souhaite vérifier.
     * @return hashmap avec la liste des personnes ainsi que le numéro de la
     *         caserne.
     * @throws IOException    si une erreur est rencontrée lors de la lecture des
     *                        données.
     * @throws ParseException si une erreur est rencontrée lors de l'analyse des
     *                        dates de naissance.
     */
    public HashMap<String, Object> findPersonsAndFireStationAtTheAddress(String address)
	    throws IOException, ParseException;
}
