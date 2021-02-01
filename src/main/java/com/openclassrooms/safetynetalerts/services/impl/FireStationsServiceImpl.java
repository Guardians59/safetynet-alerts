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

import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.DBRepository;
import com.openclassrooms.safetynetalerts.services.IFireStationsService;

@Repository
public class FireStationsServiceImpl implements IFireStationsService {

    @Autowired
    DBRepository repository;

    private static final Logger logger = LogManager.getLogger("FireStationsServieImpl");
    private int numberOfStationsFound;
    private int numberOfMajorPerson;
    private int numberOfMinorPerson;

    @Override
    public List<FireStationsModel> findAll() throws IOException {

	return repository.getFireStations();
    }

    @Override
    public HashMap<String, Object> findPersonsByStationNumber(int station) throws IOException, ParseException {

	HashMap<String, Object> result = new HashMap<>();
	ArrayList<PersonsModel> listPersons = new ArrayList<>();
	ArrayList<PersonsModel> listPersonsMinor = new ArrayList<>();
	ArrayList<PersonsModel> listPersonsMajor = new ArrayList<>();
	List<FireStationsModel> listStationsModel = repository.getFireStations();
	List<PersonsModel> listPersonsModel = repository.getPersons();
	List<MedicalRecordsModel> listMedicalModel = repository.getMedicalRecords();
	int key = station;
	Date dateNow = new Date();
	numberOfMajorPerson = 0;
	numberOfMinorPerson = 0;
	numberOfStationsFound = 0;

	logger.info("Search for people covered by the station number " + key);
	listStationsModel.forEach(fireStation -> {
	    if (fireStation.getStation() == key) {
		numberOfStationsFound++;

		listPersonsModel.forEach(person -> {

		    if (person.getAddress().equals(fireStation.getAddress()) && !listPersons.contains(person)) {

			listPersons.add(person);

			listMedicalModel.forEach(birthDate -> {

			    if (birthDate.getFirstName().equals(person.getFirstName())
				    && birthDate.getLastName().equals(person.getLastName())) {

				DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				Date birthDatePerson;
				try {

				    birthDatePerson = format.parse(birthDate.getBirthdate());

				    if ((dateNow.getTime() - birthDatePerson.getTime()) / 31557600000.0 >= 19) {
					numberOfMajorPerson++;
					listPersonsMajor.add(person);
				    } else {
					numberOfMinorPerson++;
					listPersonsMinor.add(person);
				    }
				} catch (ParseException e) {
				    e.printStackTrace();
				}

			    }
			});

		    }
		});
	    }
	});

	if (numberOfStationsFound == 0) {
	    logger.error("There is no station corresponding to the number " + key);
	} else if (listPersons.isEmpty() && numberOfStationsFound != 0) {
	    logger.info("There is no person in the coverage area of ​​station number " + key);
	}

	if (!listPersonsMajor.isEmpty()) {
	    result.put("personsMajor", listPersonsMajor);

	} else if (listPersonsMajor.isEmpty() && numberOfStationsFound != 0) {
	    logger.info("There is no major person in the coverage area of ​​station number " + key);
	}
	if (!listPersonsMinor.isEmpty()) {
	    result.put("personsMinor", listPersonsMinor);

	} else if (listPersonsMinor.isEmpty() && numberOfStationsFound != 0) {
	    logger.info("There is no minor person in the coverage area of ​​station number " + key);
	}
	if (numberOfStationsFound != 0) {
	    result.put("numberOfMajorPerson", numberOfMajorPerson);
	    result.put("numberOfMinorPerson", numberOfMinorPerson);
	}
	return result;

    }

    public FireStationsModel saveFireStation(FireStationsModel fireStation) throws IOException {
	repository.getFireStations().add(fireStation);
	
	return fireStation;
    }

}
