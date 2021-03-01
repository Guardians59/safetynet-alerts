package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.DBRepository;
import com.openclassrooms.safetynetalerts.services.IPersonsService;

/**
 * La classe PersonsServiceImpl est l'implémentation de l'interface
 * IPersonsService.
 * 
 * @see IPersonsService
 * @author Dylan
 *
 */
@Service
public class PersonsServiceImpl implements IPersonsService {

    @Autowired
    DBRepository repository;

    private static Logger logger = LogManager.getLogger(PersonsServiceImpl.class);
    private int personPresentInList;

    @Override
    public List<PersonsModel> findAll() throws IOException {
	List<PersonsModel> listPersons = repository.getPersons();
	ArrayList<PersonsModel> result = new ArrayList<>();
	logger.debug("Search the list of registered persons");
	listPersons.forEach(personsRegister -> {
	    if (!result.contains(personsRegister)) {
		result.add(personsRegister);
	    }
	});
	return result;
    }

    @Override
    public boolean saveNewPerson(PersonsModel newPerson) throws IOException {
	List<PersonsModel> list = repository.getPersons();
	boolean result = false;

	/**
	 * Si les informations de la personne sont valides, nous vérifions si elle n'est
	 * pas déjà présente dans la liste, auquel cas nous l'ajoutons dans la liste et
	 * indiquons true au boolean afin de valider l'ajout, sinon nous le laissons sur
	 * false afin d'indiquer que l'ajout ne s'est pas executé.
	 */
	if (newPerson.getFirstName() != null && newPerson.getLastName() != null && newPerson.getAddress() != null
		&& newPerson.getCity() != null && newPerson.getZip() >= 1 && newPerson.getPhone() != null
		&& newPerson.getEmail() != null) {
	    logger.debug("Adding the new person");

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

    @Override
    public boolean updatePerson(PersonsModel person) throws IOException {
	List<PersonsModel> list = repository.getPersons();
	personPresentInList = 0;
	boolean result = false;
	boolean personAlreadyInTheList = true;

	/**
	 * Si les informations sont valides, nous vérifions que les informations ont
	 * bien été modifiées et ne sois pas un doublon, dans ce cas nous utilisons une
	 * boucle forEach afin de retrouver la personne avec son prénom et nom afin de
	 * modifier ses informations et indiquons true au boolean pour valider la mis à
	 * jour, sinon nous le laissons sur false afin d'indiquer que la mis à jour ne
	 * s'est pas executée.
	 */
	if (person.getFirstName() != null && person.getLastName() != null && person.getAddress() != null
		&& person.getCity() != null && person.getZip() >= 1 && person.getPhone() != null
		&& person.getEmail() != null) {
	    logger.debug("Updating " + person.getFirstName() + " " + person.getLastName());

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

    @Override
    public boolean deletePerson(String firstName, String lastName) throws IOException {
	List<PersonsModel> list = repository.getPersons();
	PersonsModel person = new PersonsModel();
	personPresentInList = 0;
	boolean result = false;

	/**
	 * Nous vérifions que le prénom et nom soient valides, puis nous utilisons une
	 * boucle forEach afin de retrouvée la personne dans la liste, nous la
	 * supprimons de celle-ci, et vérifions qu'elle n'est plus présente afin de
	 * passer le boolean sur true pour valider la suppression, sinon nous le
	 * laissons sur false pour indiquer que la suppression ne s'est pas executée.
	 */
	if (firstName != null && lastName != null) {
	    logger.debug("Deleting " + firstName + " " + lastName);

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
