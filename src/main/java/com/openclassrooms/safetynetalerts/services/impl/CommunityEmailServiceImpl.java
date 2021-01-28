package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.PersonsRepository;
import com.openclassrooms.safetynetalerts.services.ICommunityEmailService;

@Repository
public class CommunityEmailServiceImpl implements ICommunityEmailService {

    @Autowired
    PersonsRepository personsRepository;

    private static final Logger logger = LogManager.getLogger("CommunityEmailServiceImpl");

    @Override
    public HashMap<String, Object> findEmail(String city) throws IOException {
	HashMap<String, Object> result = new HashMap<>();
	ArrayList<String> listEmail = new ArrayList<>();
	ArrayList<PersonsModel> listPersonsFound = new ArrayList<>();
	List<PersonsModel> list = personsRepository.findAll();

	logger.info("Search for emails of people in the city of " + city);
	list.forEach(person -> {
	    if (person.getCity().equals(city) && !listPersonsFound.contains(person)) {
		listEmail.add(person.getEmail());
		listPersonsFound.add(person);
	    }
	});
	if (listPersonsFound.isEmpty()) {
	    logger.error("There is no information on the people present in the city " + city
		    + " or the name of the city is invalid");
	} else {
	    result.put("email", listEmail);
	}
	return result;
	
    }

}
