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

import com.openclassrooms.safetynetalerts.models.ChildAlertModel;
import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.DBRepository;
import com.openclassrooms.safetynetalerts.services.IChildAlertService;

/**
 * La classe ChildAlertServiceImpl est l'implémentation de l'interface
 * IChildAlertService.
 * 
 * @see IChildAlertService
 * @author Dylan
 *
 */
@Service
public class ChildAlertServiceImpl implements IChildAlertService {

    @Autowired
    DBRepository repository;

    private static Logger logger = LogManager.getLogger(ChildAlertServiceImpl.class);
    private int personsAtTheAddress;
    private int personsMinor;
    private int personsMajor;

    @Override
    public HashMap<String, Object> findChildAtAddress(String address) throws IOException, ParseException {

	HashMap<String, Object> result = new HashMap<>();
	ArrayList<ChildAlertModel> listPersons = new ArrayList<>();
	ArrayList<ChildAlertModel> listPersonsMinor = new ArrayList<>();
	List<PersonsModel> listPersonsModel = repository.getPersons();
	List<MedicalRecordsModel> listMedicalModel = repository.getMedicalRecords();
	String key = address;
	Date dateNow = new Date();
	personsAtTheAddress = 0;
	personsMinor = 0;
	personsMajor = 0;

	logger.debug("Search if there are a children at the address: " + address);

	/**
	 * On vérifie avec une boucle forEach si une personne à la même adresse que
	 * celle indiquée en paramètre, si c'est le cas nous utilisons une deuxième
	 * boucle pour vérifier les dossiers médicaux afin de récupérer la date de
	 * naissance de celle-ci, nous arrondissons le dateFormat qui est millisecondes
	 * en année afin de trier les personnes, celles qui auront moins de 19 ans
	 * seront donc dans la liste personsMinor et celle de 19 ans et plus dans la
	 * liste personsMajor. Si aucun enfant n'est présent dans le foyer alors nous ne
	 * renvoyons pas de résultat, sinon nous renvoyons les deux listes en les
	 * ajoutant à la hashmap.
	 */
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
				personsMinor++;

			    } else {
				double value = (dateNow.getTime() - birthDatePerson.getTime()) / 31557600000.0;
				int age = (int) value;
				infosPerson.setFirstName(person.getFirstName());
				infosPerson.setLastName(person.getLastName());
				infosPerson.setAge(age);
				listPersons.add(infosPerson);
				personsMajor++;
			    }
			}

			catch (ParseException e) {
			    logger.error("Error when parsing birthdate", e);
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
	    logger.info(personsMinor + " person(s) minor and " + personsMajor
		    + " person(s) major found at this address " + address);
	} else if (listPersonsMinor.isEmpty()) {
	    logger.info("There are no children 18 years or younger at this address: " + address);
	}

	return result;
    }

}
