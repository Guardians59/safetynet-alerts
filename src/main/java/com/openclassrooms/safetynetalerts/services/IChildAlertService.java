package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

/**
 * L'interface IChildAlertService est le service qui nous permet de renvoyer la
 * liste des enfants présents à l'adresse indiquée avec leurs prénoms, noms et
 * âges, ceci renvoit également une liste des autres personnes présentes au sein
 * du foyer avec les mêmes informations que les enfants.
 * 
 * @author Dylan
 *
 */
public interface IChildAlertService {

    /**
     * La méthode findChildAtAddress vérifie si des enfants sont présents à
     * l'adresse indiquée.
     * 
     * @param address l'adresse que l'on souhaite vérifier.
     * @return hashmap avec les listes des personnes mineurs et majeurs, ou une
     *         chaine vide si il n'y a pas d'enfant présent au sein du foyer.
     * @throws IOException    si une erreur est rencontrée lors de la lecture des
     *                        données.
     * @throws ParseException si une erreur est rencontrée lors de l'analyse des
     *                        dates de naissance.
     */
    public HashMap<String, Object> findChildAtAddress(String address) 
	    throws IOException, ParseException;
}
