package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.models.FireModel;
import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.DBRepository;
import com.openclassrooms.safetynetalerts.services.IFireService;

/**
 * La classe FireServiceImpl est l'implémentation de l'interface IFireService.
 * 
 * @see IFireService
 * @author Dylan
 *
 */
@Service
public class FireServiceImpl implements IFireService {

    @Autowired
    DBRepository repository;

    private static Logger logger = LogManager.getLogger(FireServiceImpl.class);
    private int numberOfPersons;

    @Override
    public HashMap<String, Object> findPersonsAndFireStationAtTheAddress(String address)
	    throws IOException, ParseException {

	HashMap<String, Object> result = new HashMap<>();
	ArrayList<FireModel> listPersons = new ArrayList<>();
	ArrayList<Integer> stationNumber = new ArrayList<>();
	List<PersonsModel> listPersonsModel = repository.getPersons();
	List<MedicalRecordsModel> listMedicalModel = repository.getMedicalRecords();
	List<FireStationsModel> listFireStations = repository.getFireStations();
	String key = address;
	Date dateNow = new Date();
	numberOfPersons = 0;

	logger.debug("Search the persons living at this address " + key);

	/**
	 * On vérifie avec une boucle forEach si une personne à la même adresse que
	 * celle indiquée en paramètre, si tel est le cas on utilise une autre boucle
	 * pour récupérer le dossier médical de la personne et calculer son âge, pour
	 * ajouter ensuite cette personne à la liste. On vérifie avec une boucle quelle
	 * station intervient à cette adresse. On renvoit les résultats dans une
	 * hashmap.
	 */
	listPersonsModel.forEach(person -> {
	    if (person.getAddress().equals(key)) {
		FireModel infosPerson = new FireModel();
		infosPerson.setLastName(person.getLastName());
		infosPerson.setPhoneNumber(person.getPhone());
		numberOfPersons++;

		listMedicalModel.forEach(medicalInfosPerson -> {
		    if (medicalInfosPerson.getFirstName().equals(person.getFirstName())
			    && medicalInfosPerson.getLastName().equals(person.getLastName())) {

			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			Date birthDatePerson;

			try {

			    birthDatePerson = format.parse(medicalInfosPerson.getBirthdate());

			    double value = (dateNow.getTime() - birthDatePerson.getTime()) / 31557600000.0;
			    int age = (int) value;
			    infosPerson.setAge(age);
			    infosPerson.setMedications(medicalInfosPerson.getMedications());
			    infosPerson.setAllergies(medicalInfosPerson.getAllergies());
			    listPersons.add(infosPerson);

			} catch (ParseException e) {
			    logger.error("Error when parsing birthdate", e);
			}
		    }
		});
	    }
	});
	logger.debug("Search the station number serving this address");
	listFireStations.forEach(station -> {
	    if (station.getAddress().equals(key)) {
		int number = station.getStation();
		stationNumber.add(number);
	    }
	});

	if (listPersons.isEmpty() && stationNumber.isEmpty()) {
	    logger.error("Invalid address");
	} else if (listPersons.isEmpty() && !stationNumber.isEmpty()) {
	    logger.info("There is no information about the people covered by the station");
	} else {
	    result.put("persons", listPersons);
	    result.put("stationNumber", stationNumber);
	    logger.info(numberOfPersons + " persons found at this address " + key
		    + " which is covered by the station number " + stationNumber.toString());
	}
	return result;
    }

}
