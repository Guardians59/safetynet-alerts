package com.openclassrooms.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

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

	// WHEN
	persons = personsServiceImpl.findAll().get(22);
	
	// THEN
	assertEquals(expectedLastFirstName, persons.getFirstName());
	assertEquals(expectedLastLastName, persons.getLastName());
	assertEquals(expectedLastPhone, persons.getPhone());
    }

}
