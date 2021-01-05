package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.FireStationsRepository;
import com.openclassrooms.safetynetalerts.repository.PersonsRepository;
import com.openclassrooms.safetynetalerts.services.IFireStationsService;

@Repository
public class FireStationsServiceImpl implements IFireStationsService {

    @Autowired
    FireStationsRepository fireStationsRepository;
    @Autowired
    PersonsRepository personsRepository;

    private static final Logger logger = LogManager.getLogger("FireStationsServieImpl");

    @Override
    public List<FireStationsModel> findAll() throws IOException {

	return fireStationsRepository.findAll();
    }

    @Override
    public ArrayList<PersonsModel> findByStationNumber(int station) throws IOException {

	ArrayList<PersonsModel> result = new ArrayList<>();
	List<FireStationsModel> listStations = fireStationsRepository.findAll();
	List<PersonsModel> listPersons = personsRepository.findAll();
	int key = station;
	int numberOfStationsFound = 0;

	logger.info("Search for people covered by the station number " + key);
	listStations.forEach(fireStation -> {
	    
	    if (fireStation.getStation() == key) {
		numberOfStationsFound ++;
		listPersons.forEach(person -> {
		    if (person.getAddress().equals(fireStation.getAddress()) && !result.contains(person)) {
			result.add(person);

		    }

		});

	    }
	    
	});
	if (numberOfStationsFound == 0) {
	    logger.error("There is no station corresponding to the number " + key);
	} else if (result.isEmpty()) {
	    logger.info("There is no person in the coverage area of ​​station number " + key);
	} 
	return result;

    }

}
