package com.openclassrooms.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.services.impl.PersonsServiceImpl;

@SpringBootTest
public class PersonsServiceTest {

    @Autowired
    PersonsServiceImpl personsServiceImpl;

    @Test
    @DisplayName("Test que les attributs ne renvoient pas null")
    public void getPersonsTest() throws IOException {
	// GIVEN
	PersonsModel persons = new PersonsModel();

	// WHEN
	persons = personsServiceImpl.findAll().get(0);

	// THEN
	assertNotNull(persons.getFirstName());
	assertNotNull(persons.getLastName());
	assertNotNull(persons.getAddress());
	assertNotNull(persons.getCity());
	assertNotNull(persons.getZip());
	assertNotNull(persons.getPhone());
	assertNotNull(persons.getEmail());
    }

    @Test
    @DisplayName("Test que l'on récupére bien la liste jusqu'à la dernière personne du JSON")
    public void getNameLastPersonsTest() throws IOException {
	// GIVEN
	PersonsModel persons = new PersonsModel();
	String expectedLastFirstName = "Eric";
	String expectedLastLastName = "Cadigan";
	String expectedLastPhone = "841-874-7458";
	int size = personsServiceImpl.findAll().size();

	// WHEN
	persons = personsServiceImpl.findAll().get(size - 1);

	// THEN
	assertEquals(expectedLastFirstName, persons.getFirstName());
	assertEquals(expectedLastLastName, persons.getLastName());
	assertEquals(expectedLastPhone, persons.getPhone());
    }

    @Test
    @DisplayName("Test de l'ajout d'une personne")
    public void addNewPersonTest() throws IOException {
	// GIVEN
	List<PersonsModel> list = new ArrayList<>();
	PersonsModel person = new PersonsModel();
	person.setFirstName("Rick");
	person.setLastName("Stones");
	person.setAddress("17 haute rue");
	person.setCity("Culver");
	person.setZip(59242);
	person.setPhone("066-669-2417");
	person.setEmail("rick@mail.com");

	// WHEN
	personsServiceImpl.saveNewPerson(person);
	list = personsServiceImpl.findAll();

	// THEN
	assertEquals(list.contains(person), true);
    }

    @Test
    @DisplayName("Test de l'ajout d'une personne déjà présente")
    public void addPersonTest() throws IOException {
	// GIVEN
	List<PersonsModel> list = new ArrayList<>();
	PersonsModel person = new PersonsModel();
	person.setFirstName("Sophia");
	person.setLastName("Zemicks");
	person.setAddress("892 Downing Ct");
	person.setCity("Culver");
	person.setZip(97451);
	person.setPhone("841-874-7878");
	person.setEmail("soph@email.com");
	int numberOfPerson = 0;

	// WHEN
	personsServiceImpl.saveNewPerson(person);
	list = personsServiceImpl.findAll();
	Iterator<PersonsModel> iterator = list.iterator();
	while (iterator.hasNext()) {
	    PersonsModel personList = new PersonsModel();
	    personList = iterator.next();
	    if (personList.equals(person)) {
		numberOfPerson++;
	    }
	}

	// THEN
	assertEquals(numberOfPerson, 1);
    }

    @Test
    @DisplayName("Test de l'ajout d'une personne non valide, information manquante")
    public void addNewPersonErrorTest() throws IOException {
	// GIVEN
	List<PersonsModel> list = new ArrayList<>();
	PersonsModel person = new PersonsModel();
	person.setFirstName("Rick");
	person.setLastName("Stones");
	person.setAddress("17 haute rue");
	person.setZip(59242);
	person.setPhone("066-669-2417");
	person.setEmail("rick@mail.com");

	// WHEN
	personsServiceImpl.saveNewPerson(person);
	list = personsServiceImpl.findAll();

	// THEN
	assertEquals(list.contains(person), false);
    }

    @Test
    @DisplayName("Test de la mis à jour d'une personne")
    public void updatePersonTest() throws IOException {
	// GIVEN
	List<PersonsModel> list = new ArrayList<>();
	PersonsModel oldPerson = new PersonsModel();
	oldPerson.setFirstName("Clive");
	oldPerson.setLastName("Ferguson");
	oldPerson.setAddress("748 Townings Dr");
	oldPerson.setCity("Culver");
	oldPerson.setZip(97451);
	oldPerson.setPhone("841-874-6741");
	oldPerson.setEmail("clivfd@ymail.com");
	PersonsModel person = new PersonsModel();
	person.setFirstName("Clive");
	person.setLastName("Ferguson");
	person.setAddress("17 haute rue");
	person.setCity("Culver");
	person.setZip(59242);
	person.setPhone("066-669-2419");
	person.setEmail("clive@mail.com");

	// WHEN
	personsServiceImpl.updatePerson(person);
	list = personsServiceImpl.findAll();

	// THEN
	assertEquals(list.contains(person), true);
	assertEquals(list.contains(oldPerson), false);
    }

    @Test
    @DisplayName("Test tentative de mis à jour sans modification")
    public void updatePersonWithSameInfoTest() throws IOException {

	// GIVEN
	List<PersonsModel> list = new ArrayList<>();
	PersonsModel person = new PersonsModel();
	person.setFirstName("Sophia");
	person.setLastName("Zemicks");
	person.setAddress("892 Downing Ct");
	person.setCity("Culver");
	person.setZip(97451);
	person.setPhone("841-874-7878");
	person.setEmail("soph@email.com");
	int numberOfPerson = 0;

	// WHEN
	personsServiceImpl.updatePerson(person);
	list = personsServiceImpl.findAll();
	Iterator<PersonsModel> iterator = list.iterator();
	while (iterator.hasNext()) {
	    PersonsModel personList = new PersonsModel();
	    personList = iterator.next();
	    if (personList.equals(person)) {
		numberOfPerson++;
	    }
	}

	// THEN
	assertEquals(numberOfPerson, 1);
	assertEquals(list.contains(person), true);
    }

    @Test
    @DisplayName("Test de la mis à jour d'une personne non présente dans la liste")
    public void updatePersonErrorTest() throws IOException {
	// GIVEN
	List<PersonsModel> list = new ArrayList<>();
	PersonsModel person = new PersonsModel();
	person.setFirstName("John");
	person.setLastName("Boydd");
	person.setAddress("17 haute rue");
	person.setCity("Culver");
	person.setZip(59242);
	person.setPhone("066-669-2417");
	person.setEmail("john@mail.com");

	// WHEN
	personsServiceImpl.updatePerson(person);
	list = personsServiceImpl.findAll();

	// THEN
	assertEquals(list.contains(person), false);
    }

    @Test
    @DisplayName("Test de la suppression d'une personne")
    public void deletePersonTest() throws IOException {
	// GIVEN
	List<PersonsModel> list = new ArrayList<>();
	String firstName = "John";
	String lastName = "Boyd";

	// WHEN
	personsServiceImpl.deletePerson(firstName, lastName);
	list = personsServiceImpl.findAll();

	// THEN
	assertNotEquals(list.get(0).getFirstName(), "John");
	assertNotEquals(list.get(0).getPhone(), "841-874-6512");
    }

    @Test
    @DisplayName("Test de la tentative de suppression d'une personne non présente")
    public void deletePersonErrorTest() throws IOException {
	// GIVEN
	String firstName = "Alphonse";
	String lastName = "Boyd";

	// THEN
	assertEquals(personsServiceImpl.deletePerson(firstName, lastName), false);
    }

}
