package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.DBRepository;
import com.openclassrooms.safetynetalerts.services.IPhoneAlertService;

/**
 * La classe PhoneAlertServiceImpl est l'implémentation de l'interface
 * IPhoneAlertService.
 * 
 * @see IPhoneAlertService
 * @author Dylan
 *
 */
@Service
public class PhoneAlertServiceImpl implements IPhoneAlertService {

    @Autowired
    DBRepository repository;

    private static Logger logger = LogManager.getLogger(PhoneAlertServiceImpl.class);
    private int numberOfStationsFound;
    private int phoneNumbersFound;

    @Override
    public HashMap<String, Object> findPhoneNumberPersonByTheStationNumber(int station) throws IOException {

	HashMap<String, Object> result = new HashMap<>();
	ArrayList<String> listPhone = new ArrayList<>();
	ArrayList<PersonsModel> listPersons = new ArrayList<>();
	List<FireStationsModel> listStationsModel = repository.getFireStations();
	List<PersonsModel> listPersonsModel = repository.getPersons();
	int key = station;
	numberOfStationsFound = 0;
	phoneNumbersFound = 0;

	logger.debug("Search the phone numbers of people covered by the station number " + key);

	/**
	 * Nous utilisons une boucle forEach dans la liste des casernes afin de
	 * retrouver les adresses couvertes par celle-ci, nous utilisons une deuxième
	 * boucle dans la liste des personnes afin de trouver celles présentes à ces
	 * adresses et récupérons les numéros de téléphone dans la listPhone que nous
	 * renvoyons ensuite dans la hashmap.
	 */
	listStationsModel.forEach(fireStation -> {
	    if (fireStation.getStation() == key) {
		numberOfStationsFound++;

		listPersonsModel.forEach(person -> {

		    if (person.getAddress().equals(fireStation.getAddress()) && !listPersons.contains(person)) {
			listPersons.add(person);
			if(!listPhone.contains(person.getPhone())) {
			    listPhone.add(person.getPhone());
			    phoneNumbersFound++;
			}
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
	    logger.info(phoneNumbersFound + " phone numbers found");
	}

	return result;
    }

}
