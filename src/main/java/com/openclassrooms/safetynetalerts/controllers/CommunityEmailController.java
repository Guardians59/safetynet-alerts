package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.services.ICommunityEmailService;

/**
 * La classe CommunityEmailController permet de créer l'url communityEmail qui
 * reçoit en paramètre une ville, ceux-ci va nous renvoyer une liste des E-mails
 * des personnes présentes dans la ville.
 * 
 * @author Dylan
 *
 */
@RestController
public class CommunityEmailController {

    @Autowired
    ICommunityEmailService communityEmailService;

    /**
     * Reçoit la liste des E-mails des personnes présentes dans la ville.
     * @param city la ville où l'on veut récupérer les E-mails.
     * @return hashmap des E-mails.
     * @throws IOException si une erreur est rencontrée lors de la lecture des données.
     */
    @GetMapping(value = "communityEmail")
    public HashMap<String, Object> getEmailPersonsInTheCity(@RequestParam(value = "city") String city)
	    throws IOException {
	return communityEmailService.findEmail(city);
    }
}
