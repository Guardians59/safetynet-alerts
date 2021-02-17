package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.DBRepository;
import com.openclassrooms.safetynetalerts.services.IPersonsService;

@Repository
public class PersonsServiceImpl implements IPersonsService {

    @Autowired
    DBRepository repository;

    private static final Logger logger = LogManager.getLogger("PersonsServiceImpl");
    private int personPresentInList;

    @Override
    public List<PersonsModel> findAll() throws IOException {
	List<PersonsModel> listPersons = repository.getPersons();
	ArrayList<PersonsModel> result = new ArrayList<>();
	listPersons.forEach(personsRegister -> {
	    if (!result.contains(personsRegister)) {
		result.add(personsRegister);
	    }
	});
	return result;
    }

    public boolean saveNewPerson(PersonsModel newPerson) throws IOException {
	List<PersonsModel> list = repository.getPersons();
	boolean result = false;

	if (newPerson.getFirstName() != null && newPerson.getLastName() != null && newPerson.getAddress() != null
		&& newPerson.getCity() != null && newPerson.getZip() >= 1 && newPerson.getPhone() != null
		&& newPerson.getEmail() != null) {

	    if (!list.contains(newPerson)) {
		list.add(newPerson);
		logger.info("The person " + newPerson.getFirstName() + " " + newPerson.getLastName()
			+ " was added successfully");
		result = true;
	    } else if (list.contains(newPerson)) {
		logger.error("The person " + newPerson.getFirstName() + " " + newPerson.getLastName()
			+ " is already in the list");
	    } else {
		logger.error("Error encountered while adding the person");
	    }
	} else {
	    logger.error("Invalid informations, check that you have entered all the information correctly");
	}
	return result;

    }

    public boolean updatePerson(PersonsModel person) throws IOException {
	List<PersonsModel> list = repository.getPersons();
	personPresentInList = 0;
	boolean result = false;
	boolean personAlreadyInTheList = true;

	if (person.getFirstName() != null && person.getLastName() != null && person.getAddress() != null
		&& person.getCity() != null && person.getZip() >= 1 && person.getPhone() != null
		&& person.getEmail() != null) {

	    if (!list.contains(person)) {
		personAlreadyInTheList = false;
		list.forEach(personRegister -> {
		    if (personRegister.getFirstName().equals(person.getFirstName())
			    && personRegister.getLastName().equals(person.getLastName())) {
			personRegister.setAddress(person.getAddress());
			personRegister.setCity(person.getCity());
			personRegister.setEmail(person.getEmail());
			personRegister.setPhone(person.getPhone());
			personRegister.setZip(person.getZip());
			personPresentInList = 1;
		    }
		});
	    }
	    if (personAlreadyInTheList) {
		logger.info("The person " + person.getFirstName() + " " + person.getLastName()
			+ " is already in the list with the same informations");

	    } else if (personPresentInList == 1) {
		logger.info("The person " + person.getFirstName() + " " + person.getLastName() + " update validated");
		result = true;
	    } else if (personPresentInList == 0) {
		logger.error("No person corresponds with the indicated " + person.getFirstName() + " "
			+ person.getLastName());
	    } else {
		logger.error("Error encountered while updating the person");
	    }
	} else {
	    logger.error("Invalid informations, check that you have entered all the information correctly");
	}
	return result;
    }

    public boolean deletePerson(String firstName, String lastName) throws IOException {
	List<PersonsModel> list = repository.getPersons();
	PersonsModel person = new PersonsModel();
	personPresentInList = 0;
	boolean result = false;

	if (firstName != null && lastName != null) {

	    list.forEach(personRegister -> {
		if (personRegister.getFirstName().equals(firstName) && personRegister.getLastName().equals(lastName)) {
		    person.setAddress(personRegister.getAddress());
		    person.setCity(personRegister.getCity());
		    person.setEmail(personRegister.getEmail());
		    person.setFirstName(personRegister.getFirstName());
		    person.setLastName(personRegister.getLastName());
		    person.setPhone(personRegister.getPhone());
		    person.setZip(personRegister.getZip());
		    personPresentInList = 1;

		}
	    });

	    if (list.contains(person)) {
		list.remove(person);
	    }
	    if (personPresentInList == 0) {
		logger.error("No person corresponds to the name of " + firstName + " " + lastName);
	    } else if (!list.contains(person)) {
		logger.info(firstName + " " + lastName + " delete with success");
		result = true;
	    } else {
		logger.error("Error encountered while deleting the person");
	    }
	} else {
	    logger.error("Invalid informations, check that you have entered firstname and lastname correctly");
	}
	return result;

    }

}
