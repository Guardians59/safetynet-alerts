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
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.models.FireModel;
import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.DBRepository;
import com.openclassrooms.safetynetalerts.services.IFireService;

@Repository
public class FireServiceImpl implements IFireService {

    @Autowired
    DBRepository repository;

    private static final Logger logger = LogManager.getLogger("FireServiceIpml");

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

	logger.info("Search the persons living at this address " + key);
	listPersonsModel.forEach(person -> {
	    if (person.getAddress().equals(key)) {
		FireModel infosPerson = new FireModel();
		infosPerson.setLastName(person.getLastName());
		infosPerson.setPhoneNumber(person.getPhone());

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
			    e.printStackTrace();
			}
		    }
		});
	    }
	});
	logger.info("Search the station number serving this address");
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
	}
	return result;
    }

}
