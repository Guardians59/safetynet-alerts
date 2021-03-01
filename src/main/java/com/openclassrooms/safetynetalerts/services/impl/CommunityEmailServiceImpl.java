package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.DBRepository;
import com.openclassrooms.safetynetalerts.services.ICommunityEmailService;

/**
 * La classe CommunityEmailServiceImpl est l'implémentation de l'interface
 * ICommunityEmailService.
 * 
 * @see ICommunityEmailService
 * @author Dylan
 *
 */
@Service
public class CommunityEmailServiceImpl implements ICommunityEmailService {

    @Autowired
    DBRepository repository;

    private static Logger logger = LogManager.getLogger(CommunityEmailServiceImpl.class);
    private int numberOfMails;

    @Override
    public HashMap<String, Object> findEmail(String city) throws IOException {
	HashMap<String, Object> result = new HashMap<>();
	ArrayList<String> listEmail = new ArrayList<>();
	ArrayList<PersonsModel> listPersonsFound = new ArrayList<>();
	List<PersonsModel> list = repository.getPersons();
	numberOfMails = 0;

	logger.debug("Search for emails of people in the city of " + city);

	/**
	 * On vérifie avec une boucle forEach si des personnes habitent la même ville
	 * que celle indiquée en paramètre, si c'est le cas on ajoute le mail de ces
	 * personnes dans la liste des E-mails, on renvoit ensuite le résultat dans une
	 * hashmap.
	 */
	list.forEach(person -> {
	    if (person.getCity().equals(city) && !listPersonsFound.contains(person)) {
		listPersonsFound.add(person);
		if(!listEmail.contains(person.getEmail())) {
		    listEmail.add(person.getEmail());
		    numberOfMails++;
		}
	    }
	});
	if (listPersonsFound.isEmpty()) {
	    logger.error("There is no information on the people present in the city " + city
		    + " or the name of the city is invalid");
	} else {
	    result.put("email", listEmail);
	    logger.info(numberOfMails + " mails found in the city " + city);
	}
	return result;

    }

}
