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

import com.openclassrooms.safetynetalerts.models.ChildAlertModel;
import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.MedicalRecordsRepository;
import com.openclassrooms.safetynetalerts.repository.PersonsRepository;
import com.openclassrooms.safetynetalerts.services.IChildAlertService;

@Repository
public class ChildAlertServiceImpl implements IChildAlertService {

    @Autowired
    PersonsRepository personsRepository;

    @Autowired
    MedicalRecordsRepository medicalRecordsRepository;

    private static final Logger logger = LogManager.getLogger("ChildAlertServiceImpl");
    private int personsAtTheAddress;

    @Override
    public HashMap<String, Object> findChildAtAddress(String address) throws IOException, ParseException {

	HashMap<String, Object> result = new HashMap<>();
	ArrayList<ChildAlertModel> listPersons = new ArrayList<>();
	ArrayList<ChildAlertModel> listPersonsMinor = new ArrayList<>();
	List<PersonsModel> listPersonsModel = personsRepository.findAll();
	List<MedicalRecordsModel> listMedicalModel = medicalRecordsRepository.findAll();

	String key = address;
	Date dateNow = new Date();
	personsAtTheAddress = 0;

	logger.info("Search if there are a children at the address: " + address);

	listPersonsModel.forEach(person -> {
	    if (person.getAddress().equals(key)) {
		personsAtTheAddress++;
		listMedicalModel.forEach(searchBirthDate -> {

		    if (searchBirthDate.getFirstName().equals(person.getFirstName())
			    && searchBirthDate.getLastName().equals(person.getLastName())) {

			ChildAlertModel infosPerson = new ChildAlertModel();
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			Date birthDatePerson;
			try {

			    birthDatePerson = format.parse(searchBirthDate.getBirthdate());

			    if ((dateNow.getTime() - birthDatePerson.getTime()) / 31557600000.0 <= 18.9) {

				double value = (dateNow.getTime() - birthDatePerson.getTime()) / 31557600000.0;
				int age = (int) value;
				infosPerson.setFirstName(person.getFirstName());
				infosPerson.setLastName(person.getLastName());
				infosPerson.setAge(age);
				listPersonsMinor.add(infosPerson);

			    } else {
				double value = (dateNow.getTime() - birthDatePerson.getTime()) / 31557600000.0;
				int age = (int) value;
				infosPerson.setFirstName(person.getFirstName());
				infosPerson.setLastName(person.getLastName());
				infosPerson.setAge(age);
				listPersons.add(infosPerson);

			    }
			}

			catch (ParseException e) {
			    e.printStackTrace();
			}

		    }
		});
	    }
	});
	if (personsAtTheAddress == 0) {
	    logger.error("Invalid address");
	} else if (!listPersonsMinor.isEmpty()) {
	    result.put("childrenLivingAtThisAddress", listPersonsMinor);
	    result.put("personMajorLivingAtThisAddress", listPersons);
	} else if (listPersonsMinor.isEmpty()) {
	    logger.info("There are no children 18 years or younger at this address: " + address);
	}

	return result;
    }

}
