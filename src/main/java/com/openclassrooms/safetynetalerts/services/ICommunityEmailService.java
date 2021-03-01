package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.util.HashMap;

/**
 * L'interface ICommunityEmailService est le service qui nous permet de
 * renvoyer la liste d'E-mails des personnes présentes dans la ville.
 * 
 * @author Dylan
 *
 */
public interface ICommunityEmailService {
    
    /**
     * La méthode findEmail renvoit la liste des E-mails des personnes présentes
     * dans la ville entrée en paramètre.
     * 
     * @param city la ville que l'on souhaite vérifier.
     * @return hashmap avec les E-mails des personnes présentes.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    public HashMap<String, Object> findEmail(String city) throws IOException;

}
