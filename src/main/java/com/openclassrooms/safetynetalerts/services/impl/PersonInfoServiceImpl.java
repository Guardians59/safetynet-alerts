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

import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.models.PersonInfoModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.DBRepository;
import com.openclassrooms.safetynetalerts.services.IPersonInfoService;

/**
 * La classe PersonInfoServiceImpl est l'implémentation de l'interface
 * IPersonInfoService.
 * 
 * @see IPersonInfoService
 * @author Dylan
 *
 */
@Service
public class PersonInfoServiceImpl implements IPersonInfoService {

    @Autowired
    DBRepository repository;

    private static Logger logger = LogManager.getLogger(PersonInfoServiceImpl.class);

    @Override
    public HashMap<String, Object> findPersonInfoByName(String firstName, String lastName)
	    throws IOException, ParseException {

	HashMap<String, Object> result = new HashMap<>();
	ArrayList<PersonInfoModel> listPersons = new ArrayList<>();
	List<PersonsModel> listPersonsModel = repository.getPersons();
	List<MedicalRecordsModel> listMedicalRecordsModel = repository.getMedicalRecords();
	String firstKey = firstName;
	String lastKey = lastName;
	Date dateNow = new Date();

	logger.debug("Search information about people with this name: " + firstKey + " " + lastKey);

	/**
	 * Nous utilisons une boucle forEach dans la liste des personnes enregistrées
	 * afin de retrouver celle correspondant aux paramètres, nous utilisons une
	 * deuxième boucle dans les dossiers médicaux afin de récupérer ses informations
	 * médicales et ajoutons toutes les donées de la personne dans une liste, ainsi
	 * si nous avons plusieurs personnes portant le même nom, elle seront toutes
	 * ajoutées. Nous renvoyons ensuite cette liste dans la hashmap envoyer en
	 * résultat.
	 */
	listPersonsModel.forEach(person -> {
	    if (person.getFirstName().equals(firstKey) && person.getLastName().equals(lastKey)) {
		PersonInfoModel personInfoModel = new PersonInfoModel();
		personInfoModel.setLastName(person.getLastName());
		personInfoModel.setAddress(person.getAddress());
		personInfoModel.setCity(person.getCity());
		personInfoModel.setZip(person.getZip());
		personInfoModel.setMail(person.getEmail());

		listMedicalRecordsModel.forEach(medicalRecords -> {
		    if (medicalRecords.getFirstName().equals(person.getFirstName())
			    && medicalRecords.getLastName().equals(person.getLastName())) {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			Date birthDatePerson;

			try {
			    birthDatePerson = format.parse(medicalRecords.getBirthdate());
			    double value = (dateNow.getTime() - birthDatePerson.getTime()) / 31557600000.0;
			    int age = (int) value;

			    personInfoModel.setAge(age);
			    personInfoModel.setMedications(medicalRecords.getMedications());
			    personInfoModel.setAllergies(medicalRecords.getAllergies());
			    listPersons.add(personInfoModel);

			} catch (ParseException e) {
			    logger.error("Error when parsing birthdate", e);
			}

		    }
		});
	    }
	});

	if (listPersons.isEmpty()) {
	    logger.error("No person found with this name");
	} else {
	    result.put("personInfo", listPersons);
	    logger.info("Informations of " + firstName + " " + lastName + " found");

	}
	return result;
    }

}
