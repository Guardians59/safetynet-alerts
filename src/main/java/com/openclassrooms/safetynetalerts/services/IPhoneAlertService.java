package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.util.HashMap;

/**
 * L'interface IPhoneAlertService est le service permettant de retourner la
 * liste des numéros de téléphone des personnes couverts par une caserne.
 * 
 * @author Dylan
 *
 */
public interface IPhoneAlertService {

    /**
     * La méthode findPhoneNumberPersonByTheStationNumber permet de retourner la
     * liste des personnes couvertes par la caserne entrée en paramètre par le biais
     * de son numéro.
     * 
     * @param station numéro de la caserne.
     * @return hashmap avec la liste des numéros de téléphone.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    public HashMap<String, Object> findPhoneNumberPersonByTheStationNumber(int station) 
	    throws IOException;
}
