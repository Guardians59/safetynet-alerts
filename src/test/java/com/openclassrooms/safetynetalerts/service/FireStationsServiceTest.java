package com.openclassrooms.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.services.impl.FireStationsServiceImpl;

@SpringBootTest
public class FireStationsServiceTest {

    @Autowired
    private FireStationsServiceImpl fireStationsServiceImpl;

    @Test
    @DisplayName("Test que les attributs ne renvoient pas null")
    public void getStationsTest() throws IOException {
	// GIVEN
	FireStationsModel fireStation = new FireStationsModel();

	// WHEN
	fireStation = fireStationsServiceImpl.findAll().get(0);
	
	// THEN
	assertNotNull(fireStation.getAddress());
	assertNotNull(fireStation.getStation());
    }

    @Test
    @DisplayName("Test que l'on récupére bien la liste jusqu'à la dernière station du JSON")
    public void getLastFireStationTest() throws IOException {
	// GIVEN
	FireStationsModel fireStation = new FireStationsModel();
	String expectedLastStationAddress = "951 LoneTree Rd";
	int expectedLastStationNumber = 2;

	// WHEN
	fireStation = fireStationsServiceImpl.findAll().get(12);

	// THEN
	assertEquals(expectedLastStationAddress, fireStation.getAddress());
	assertEquals(expectedLastStationNumber, fireStation.getStation());
    }
    
    @Test
    @DisplayName("Test que la liste des personnes couvertes par une station valide ne renvoie pas null")
    public void getPersonsListCoveredByStation() throws IOException {
	//GIVEN
	PersonsModel person = new PersonsModel();
	
	//WHEN 
	person = fireStationsServiceImpl.findByStationNumber(1).get(0);
	
	//THEN
	assertNotNull(person.getFirstName());
	assertNotNull(person.getLastName());
	assertNotNull(person.getAddress());
	assertNotNull(person.getCity());
	assertNotNull(person.getZip());
	assertNotNull(person.getPhone());
	assertNotNull(person.getEmail());
    }
}
