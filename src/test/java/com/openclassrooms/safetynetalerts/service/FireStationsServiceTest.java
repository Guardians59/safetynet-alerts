package com.openclassrooms.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;
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
    @DisplayName("Test que la recherche par numéro de station ne renvoit pas null")
    public void getPersonsListFindByStationNumberIsNotNullTest() throws IOException, ParseException {
	//GIVEN
	HashMap<String, Object> liste = new HashMap<>();
	boolean expected = true;
	
	//WHEN
	liste = fireStationsServiceImpl.findPersonsByStationNumber(1);
	
	//THEN
	assertEquals(liste.containsKey("Persons Major"), expected);
	assertEquals(liste.containsKey("Persons Minor"), expected);
	
    }
    
    @Test
    @DisplayName("Test que la station 4 ne contient que des personnes majeures")
    public void getListStationNumber4Test() throws IOException, ParseException {
	//GIVEN
	HashMap<String, Object> liste = new HashMap<>();
	boolean expectedMajor = true;
	boolean expectedMinor = false;
	
	//WHEN
	liste = fireStationsServiceImpl.findPersonsByStationNumber(4);
	
	//THEN	
	assertEquals(liste.containsKey("Persons Minor"), expectedMinor);
	assertEquals(liste.containsKey("Persons Major"), expectedMajor);
		
	    }
    
    @Test
    @DisplayName("Test que la liste est vide quand le numéro de station est incorrect")
    public void getListWrongStationNumberTest() throws IOException, ParseException {
	//GIVEN
	HashMap<String, Object> liste = new HashMap<>();
	boolean expected = false;
	
	
	//WHEN
	liste = fireStationsServiceImpl.findPersonsByStationNumber(5);
	
	//THEN	
	assertEquals(liste.containsKey("Persons Minor"), expected);
	assertEquals(liste.containsKey("Persons Major"), expected);
    }
    }
   

