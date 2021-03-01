package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

/**
 * L'interface IPersonInfoService est le service qui nous permet de renvoyer
 * les informations (nom, adresse, âge, E-mail et antécédents médicaux) de la
 * personne indiquée en paramètre avec son prénom et nom.
 * 
 * @author Dylan
 *
 */
public interface IPersonInfoService {
    
    /**
     * La méthode findPersonInfoByName permet de renvoyer les informations (nom,
     * adresse, âge, E-mail et antécédents médicaux) de la personne entrée en
     * paramètre avec son prénom et nom.
     * 
     * @param firstName prénom de la personne.
     * @param lastName  nom de la personne.
     * @return hashmap avec les informations de la personne.
     * @throws IOException    si une erreur est rencontrée lors de la lecture des
     *                        données.
     * @throws ParseException si une erreur est rencontrée lors de l'analyse des
     *                        dates de naissance.
     */
    public HashMap<String, Object> findPersonInfoByName(String firstName, String lastName)
	    throws IOException, ParseException;

}
