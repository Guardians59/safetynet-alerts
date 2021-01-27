package com.openclassrooms.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.services.impl.FireServiceImpl;

@SpringBootTest
public class FireServiceTest {

    @Autowired
    FireServiceImpl fireServiceImpl;
    
    @Test
    @DisplayName("Test que l'on obtient un résultat avec une adresse valide")
    public void getListPersonsAndNumberStationWithValidAddressTest() throws IOException, ParseException {
	//GIVEN
	HashMap<String, Object> result = new HashMap<>();
	boolean expected = true;
	
	//WHEN
	result = fireServiceImpl.findPersonsAndFireStationAtTheAddress("1509 Culver St");
	
	//THEN
	assertEquals(result.containsKey("persons"), expected);
	assertEquals(result.containsKey("stationNumber"), expected);
	
    }
    
    @Test
    @DisplayName("Test que l'on obtient aucun résultat avec une adresse invalide")
    public void getErrorListPersonsAndNumberStationWithInvalidAddressTest() throws IOException, ParseException {
	//GIVEN
	HashMap<String, Object> result = new HashMap<>();
	boolean expected = false;
	
	//WHEN
	result = fireServiceImpl.findPersonsAndFireStationAtTheAddress("1509 Culver S");
	
	//THEN
	assertEquals(result.containsKey("persons"), expected);
	assertEquals(result.containsKey("stationNumber"), expected);
    }
    
    
}
