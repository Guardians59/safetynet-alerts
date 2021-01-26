package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.FireStationsRepository;
import com.openclassrooms.safetynetalerts.repository.PersonsRepository;
import com.openclassrooms.safetynetalerts.services.IPhoneAlertService;

@Repository
public class PhoneAlertServiceImpl implements IPhoneAlertService {
    
    @Autowired
    PersonsRepository personsRepository;
    @Autowired
    FireStationsRepository fireStationsRepository;
    
    private static final Logger logger = LogManager.getLogger("PhoneAlertServieImpl");
    private int numberOfStationsFound;

    @Override
    public HashMap<String, Object> findPhoneNumberPersonByTheStationNumber(int station) throws IOException {
	
	HashMap<String, Object> result = new HashMap<>();
	ArrayList<String> listPhone = new ArrayList<>();
	ArrayList<PersonsModel> listPersons = new ArrayList<>();
	List<FireStationsModel> listStationsModel = fireStationsRepository.findAll();
	List<PersonsModel> listPersonsModel = personsRepository.findAll();
	int key = station;
	numberOfStationsFound = 0;

	logger.info("Search the phone numbers of people covered by the station number " + key);

	listStationsModel.forEach(fireStation -> {
	    if (fireStation.getStation() == key) {
		numberOfStationsFound++;

		listPersonsModel.forEach(person -> {

		    if (person.getAddress().equals(fireStation.getAddress()) && !listPersons.contains(person)) {
			listPersons.add(person);
			listPhone.add(person.getPhone());
		    }
		});
	    }
	});
	
	if (numberOfStationsFound == 0) {
	    logger.error("There is no station corresponding to the number " + key);
	} else if (listPersons.isEmpty() && numberOfStationsFound != 0) {
	    logger.info("There is no person in the coverage area of ​​station number " + key);
	} else {
	    result.put("phoneNumbers", listPhone);
	}

	return result;
    }

}
