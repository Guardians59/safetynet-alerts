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

import com.openclassrooms.safetynetalerts.models.FloodAddressModel;
import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.FloodPersonModel;
import com.openclassrooms.safetynetalerts.models.FloodStationModel;
import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.DBRepository;
import com.openclassrooms.safetynetalerts.services.IFloodService;

@Repository
public class FloodServiceImpl implements IFloodService {

    @Autowired
    DBRepository repository;

    private static final Logger logger = LogManager.getLogger("FloodServiceIpml");
    private int key;
    private int numberOfStationsFound;
    private boolean stationFound;

    @Override
    public HashMap<String, Object> findHomesServedByTheStation(List<Integer> station)
	    throws IOException, ParseException {

	HashMap<String, Object> result = new HashMap<>();
	ArrayList<FloodStationModel> listFloodStationModel = new ArrayList<>();
	List<FireStationsModel> listStationsModel = repository.getFireStations();
	List<PersonsModel> listPersonsModel = repository.getPersons();
	List<MedicalRecordsModel> listMedicalRecordsModel = repository.getMedicalRecords();

	key = 0;
	stationFound = false;
	Date dateNow = new Date();

	station.forEach(stationNumber -> {
	    key = stationNumber.intValue();
	    numberOfStationsFound = 0;
	    FloodStationModel floodStationModel = new FloodStationModel();
	    ArrayList<FloodAddressModel> listAddressModel = new ArrayList<>();
	    ArrayList<PersonsModel> listPersonsCheckDuplicate = new ArrayList<>();

	    logger.info("Search homes served by the station number " + key);
	    listStationsModel.forEach(fireStation -> {
		if (fireStation.getStation() == key) {

		    FloodAddressModel addressModel = new FloodAddressModel();
		    numberOfStationsFound++;
		    stationFound = true;
		    ArrayList<FloodPersonModel> listFloodPersonModel = new ArrayList<>();
		    listPersonsModel.forEach(person -> {
			if (person.getAddress().equals(fireStation.getAddress())
				&& !listPersonsCheckDuplicate.contains(person)) {
			    listPersonsCheckDuplicate.add(person);
			    addressModel.setAddress(person.getAddress());
			    addressModel.setCity(person.getCity());
			    addressModel.setZip(person.getZip());

			    FloodPersonModel personInfosFloodModel = new FloodPersonModel();
			    personInfosFloodModel.setLastName(person.getLastName());
			    personInfosFloodModel.setPhoneNumber(person.getPhone());

			    listMedicalRecordsModel.forEach(medicalRecords -> {
				if (medicalRecords.getFirstName().equals(person.getFirstName())
					&& medicalRecords.getLastName().equals(person.getLastName())) {

				    DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				    Date birthDatePerson;

				    try {

					birthDatePerson = format.parse(medicalRecords.getBirthdate());

					double value = (dateNow.getTime() - birthDatePerson.getTime()) / 31557600000.0;
					int age = (int) value;
					personInfosFloodModel.setAge(age);
					personInfosFloodModel.setMedications(medicalRecords.getMedications());
					personInfosFloodModel.setAllergies(medicalRecords.getAllergies());
					listFloodPersonModel.add(personInfosFloodModel);

				    } catch (ParseException e) {
					e.printStackTrace();
				    }

				}
			    });

			}

		    });
		    addressModel.setListPersonsInHome(listFloodPersonModel);
		    if (!addressModel.getListPersonsInHome().isEmpty()) {
			listAddressModel.add(addressModel);
		    }
		}

	    });
	    floodStationModel.setStationNumber(key);
	    floodStationModel.setListHomes(listAddressModel);
	    if (!floodStationModel.getListHomes().isEmpty()) {
		listFloodStationModel.add(floodStationModel);
	    }

	    if (numberOfStationsFound == 0) {
		logger.error("There is no station corresponding to the number " + key);
	    } else if (numberOfStationsFound != 0 && listFloodStationModel.get(0).getListHomes().isEmpty()) {
		logger.info("There are no homes served by the station number " + key);
	    }

	});

	if (stationFound == true && !listFloodStationModel.get(0).getListHomes().isEmpty()) {
	    result.put("stations", listFloodStationModel);
	}
	return result;
    }

}
