package com.openclassrooms.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.services.impl.FloodServiceImpl;

@SpringBootTest
public class FloodServiceTest {
    
    @Autowired
    FloodServiceImpl floodServiceImpl;
    
    @Test
    @DisplayName("Test que l'on obtient un résultat avec des numéros de station valides")
    public void getListHomesWithStationNumberTest() throws IOException, ParseException {
	//GIVEN
	HashMap<String, Object> result = new HashMap<>();
	boolean expected = true;
	List<Integer> stationNumber = new ArrayList<>();
	stationNumber.add(1);
	stationNumber.add(2);
	stationNumber.add(3);
	
	//WHEN
	result = floodServiceImpl.findHomesServedByTheStation(stationNumber);

	//THEN
	assertEquals(result.containsKey("stations"), expected);
	
    }
    
    @Test
    @DisplayName("Test que l'on obtient un résultat avec un seul numéro de station valide")
    public void getListHomesWithStationNumberValidTest() throws IOException, ParseException {
	//GIVEN
	HashMap<String, Object> result = new HashMap<>();
	boolean expected = true;
	List<Integer> stationNumber = new ArrayList<>();
	stationNumber.add(15);
	stationNumber.add(3);
	
	//WHEN
	result = floodServiceImpl.findHomesServedByTheStation(stationNumber);

	//THEN
	assertEquals(result.containsKey("stations"), expected);
	
    
   }
    
    @Test
    @DisplayName("Test que l'on obtient pas de résultat avec un numéro de station invalide")
    public void getErrorListHomesWithStationNumberInvalidTest() throws IOException, ParseException {
	//GIVEN
	HashMap<String, Object> result = new HashMap<>();
	boolean expected = false;
	List<Integer> stationNumber = new ArrayList<>();
	stationNumber.add(18);
	
	//WHEN
	result = floodServiceImpl.findHomesServedByTheStation(stationNumber);

	//THEN
	assertEquals(result.containsKey("stations"), expected);
	
    
   }
    
 

}
