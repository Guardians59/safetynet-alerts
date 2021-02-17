package com.openclassrooms.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.PutFireStationsModel;
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
	int size = fireStationsServiceImpl.findAll().size();

	// WHEN
	fireStation = fireStationsServiceImpl.findAll().get(size - 1);

	// THEN
	assertEquals(expectedLastStationAddress, fireStation.getAddress());
	assertEquals(expectedLastStationNumber, fireStation.getStation());
    }

    @Test
    @DisplayName("Test que la recherche par numéro de station ne renvoit pas null")
    public void getPersonsListFindByStationNumberIsNotNullTest() throws IOException, ParseException {
	// GIVEN
	HashMap<String, Object> liste = new HashMap<>();
	boolean expected = true;

	// WHEN
	liste = fireStationsServiceImpl.findPersonsByStationNumber(2);

	// THEN
	assertEquals(liste.containsKey("personsMajor"), expected);
	assertEquals(liste.containsKey("personsMinor"), expected);

    }

    @Test
    @DisplayName("Test que la station 4 ne contient que des personnes majeures")
    public void getListStationNumber4Test() throws IOException, ParseException {
	// GIVEN
	HashMap<String, Object> liste = new HashMap<>();
	boolean expectedMajor = true;
	boolean expectedMinor = false;

	// WHEN
	liste = fireStationsServiceImpl.findPersonsByStationNumber(4);

	// THEN
	assertEquals(liste.containsKey("personsMinor"), expectedMinor);
	assertEquals(liste.containsKey("personsMajor"), expectedMajor);

    }

    @Test
    @DisplayName("Test que la liste est vide quand le numéro de station est incorrect")
    public void getListWrongStationNumberTest() throws IOException, ParseException {
	// GIVEN
	HashMap<String, Object> liste = new HashMap<>();
	boolean expected = false;

	// WHEN
	liste = fireStationsServiceImpl.findPersonsByStationNumber(17);

	// THEN
	assertEquals(liste.containsKey("personsMinor"), expected);
	assertEquals(liste.containsKey("personsMajor"), expected);
    }

    @Test
    @DisplayName("Test de l'ajout d'une station")
    public void addNewFireStationTest() throws IOException {
	//GIVEN
	List<FireStationsModel> list = new ArrayList<>();
	FireStationsModel station = new FireStationsModel();
	station.setAddress("17 haute rue");
	station.setStation(5);
	
	//WHEN
	fireStationsServiceImpl.saveNewFireStation(station);
	list = fireStationsServiceImpl.findAll();

	//THEN
	assertEquals(list.contains(station), true);
    }
    
    @Test
    @DisplayName("Test de l'ajout d'une station déjà présente")
    public void addFireStationTest() throws IOException {
	//GIVEN
	List<FireStationsModel> list = new ArrayList<>();
	FireStationsModel station = new FireStationsModel();
	station.setAddress("892 Downing Ct");
	station.setStation(2);
	int numberOfStation = 0;
	
	//WHEN
	fireStationsServiceImpl.saveNewFireStation(station);
	list = fireStationsServiceImpl.findAll();
	Iterator<FireStationsModel> iterator = list.iterator();
	while(iterator.hasNext()) {
	    FireStationsModel stationIterator = new FireStationsModel();
	    stationIterator = iterator.next();
	    if(stationIterator.getAddress().equals(station.getAddress()) && 
		    stationIterator.getStation() == station.getStation()) {
		numberOfStation++;
	    }
	}

	//THEN
	assertEquals(list.contains(station), true);
	assertEquals(numberOfStation, 1);
    }

    @Test
    @DisplayName("Test de l'ajout d'une station non valide, numéro manquant")
    public void addNewFireStationErrorTest() throws IOException {
	//GIVEN
	List<FireStationsModel> list = new ArrayList<>();
	FireStationsModel station = new FireStationsModel();
	station.setAddress("17 haute rue");

	//WHEN
	fireStationsServiceImpl.saveNewFireStation(station);
	list = fireStationsServiceImpl.findAll();

	//THEN
	assertEquals(list.contains(station), false);
    }
    
    @Test
    @DisplayName("Test de la mis à jour d'une station")
    public void updateFireStationTest() throws IOException {
	//GIVEN
	List<FireStationsModel> list = new ArrayList<>();
	PutFireStationsModel station = new PutFireStationsModel();
	station.setAddress("1509 Culver St");
	station.setOldStationNumber(3);
	station.setNewStationNumber(8);

	//WHEN
	fireStationsServiceImpl.updateFireStation(station);
	list = fireStationsServiceImpl.findAll();

	//THEN
	assertEquals(list.get(0).getStation(), 8);
    }
    
    @Test
    @DisplayName("Test de la mis à jour d'une station non valide, numéro incorrect")
    public void updateFireStationErrorNumberTest() throws IOException {
	//GIVEN
	List<FireStationsModel> list = new ArrayList<>();
	PutFireStationsModel station = new PutFireStationsModel();
	station.setAddress("1509 Culver St");
	station.setOldStationNumber(2);
	station.setNewStationNumber(7);

	//WHEN
	fireStationsServiceImpl.updateFireStation(station);
	list = fireStationsServiceImpl.findAll();

	//THEN
	assertNotEquals(list.get(0).getStation(), 7);
    }
    
    @Test
    @DisplayName("Test de la mis à jour d'une station non valide, adresse incorrect")
    public void updateFireStationErrorAddressTest() throws IOException {
	//GIVEN
	List<FireStationsModel> list = new ArrayList<>();
	PutFireStationsModel station = new PutFireStationsModel();
	station.setAddress("29 15th St false");
	station.setOldStationNumber(8);
	station.setNewStationNumber(7);

	//WHEN
	fireStationsServiceImpl.updateFireStation(station);
	list = fireStationsServiceImpl.findAll();

	//THEN
	assertNotEquals(list.get(1).getStation(), 7);
    }
    
    @Test
    @DisplayName("Test de la suppression d'une station par adresse")
    public void deleteFireStationAddressTest() throws IOException {
	//GIVEN
	List<FireStationsModel> list = new ArrayList<>();
	Optional<String> address;
	address = Optional.of("112 Steppes Pl");
	Optional<Integer> station = Optional.empty();
	FireStationsModel fireStationNumber3 = new FireStationsModel();
	fireStationNumber3.setAddress("112 Steppes Pl");
	fireStationNumber3.setStation(3);
	FireStationsModel fireStationNumber4 = new FireStationsModel();
	fireStationNumber4.setAddress("112 Steppes Pl");
	fireStationNumber4.setStation(4);

	//WHEN
	fireStationsServiceImpl.deleteFireStation(station, address);
	list = fireStationsServiceImpl.findAll();

	//THEN
	assertEquals(list.contains(fireStationNumber3), false);
	assertEquals(list.contains(fireStationNumber4), false);
    }
    
    @Test
    @DisplayName("Test de la suppression d'une station par numéro de station")
    public void deleteFireStationNumberTest() throws IOException {
	//GIVEN
	List<FireStationsModel> list = new ArrayList<>();
	Optional<String> address = Optional.empty();
	Optional<Integer> station = Optional.of(1);
	int size = fireStationsServiceImpl.findAll().size();
	FireStationsModel fireStationNumber1 = new FireStationsModel();
	fireStationNumber1.setAddress("644 Gershwin Cir");
	fireStationNumber1.setStation(1);
	FireStationsModel fireStationNumber2 = new FireStationsModel();
	fireStationNumber2.setAddress("908 73rd St");
	fireStationNumber2.setStation(1);
	FireStationsModel fireStationNumber3 = new FireStationsModel();
	fireStationNumber3.setAddress("947 E. Rose Dr");
	fireStationNumber3.setStation(1);

	//WHEN
	fireStationsServiceImpl.deleteFireStation(station, address);
	list = fireStationsServiceImpl.findAll();

	//THEN
	assertNotEquals(list.size(), size);
	assertEquals(list.contains(fireStationNumber1), false);
	assertEquals(list.contains(fireStationNumber2), false);
	assertEquals(list.contains(fireStationNumber3), false);
    }
    
    @Test
    @DisplayName("Test de la suppression d'une station par adresse et numéro")
    public void deleteFireStationAddressAndNumberTest() throws IOException {
	//GIVEN
	List<FireStationsModel> list = new ArrayList<>();
	Optional<String> address = Optional.of("489 Manchester St");
	Optional<Integer> station = Optional.of(4);
	FireStationsModel fireStation = new FireStationsModel();
	fireStation.setAddress("489 Manchester St");
	fireStation.setStation(4);
	
	//WHEN
	fireStationsServiceImpl.deleteFireStation(station, address);
	list = fireStationsServiceImpl.findAll();

	//THEN
	assertEquals(list.contains(fireStation), false);
	
    }
    
    @Test
    @DisplayName("Test de la tentative de suppression d'une station, adresse non connue, numéro valide")
    public void deleteFireStationValidNumberButErrorAddressTest() throws IOException {
	//GIVEN
	List<FireStationsModel> list = new ArrayList<>();
	Optional<String> address = Optional.of("748 Townings Dr");
	Optional<Integer> station = Optional.of(3);
	FireStationsModel fireStation = new FireStationsModel();
	fireStation.setAddress("748 Townings Dr");
	fireStation.setStation(3);
	
	//WHEN
	fireStationsServiceImpl.deleteFireStation(station, address);
	list = fireStationsServiceImpl.findAll();

	//THEN
	assertEquals(list.contains(fireStation), true);
	
    }
    
    @Test
    @DisplayName("Test de la tentative de suppression d'une station, numéro non connue, adresse valide")
    public void deleteFireStationValidAddressButErrorNumberTest() throws IOException {
	//GIVEN
	List<FireStationsModel> list = new ArrayList<>();
	Optional<String> address = Optional.of("748 Townings Dr");
	Optional<Integer> station = Optional.of(10);
	FireStationsModel fireStation = new FireStationsModel();
	fireStation.setAddress("748 Townings Dr");
	fireStation.setStation(3);
	
	//WHEN
	fireStationsServiceImpl.deleteFireStation(station, address);
	list = fireStationsServiceImpl.findAll();

	//THEN
	assertEquals(list.contains(fireStation), true);
	
    }
    
    @Test
    @DisplayName("Test de la tentative de suppression d'une station, numéro non connue")
    public void deleteFireStationErrorNumberTest() throws IOException {
	//GIVEN
	List<FireStationsModel> list = new ArrayList<>();
	Optional<String> address = Optional.empty();
	Optional<Integer> station = Optional.of(10);
	FireStationsModel fireStation = new FireStationsModel();
	fireStation.setAddress("748 Townings Dr");
	fireStation.setStation(3);
	
	//WHEN
	fireStationsServiceImpl.deleteFireStation(station, address);
	list = fireStationsServiceImpl.findAll();

	//THEN
	assertEquals(list.contains(fireStation), true);
	
    }
    
    @Test
    @DisplayName("Test de la tentative de suppression d'une station, numéro non connue")
    public void deleteFireStationErrorAddressTest() throws IOException {
	//GIVEN
	List<FireStationsModel> list = new ArrayList<>();
	Optional<String> address = Optional.of("748 Townings Dr false");
	Optional<Integer> station = Optional.empty();
	FireStationsModel fireStation = new FireStationsModel();
	fireStation.setAddress("748 Townings Dr");
	fireStation.setStation(3);
	
	//WHEN
	fireStationsServiceImpl.deleteFireStation(station, address);
	list = fireStationsServiceImpl.findAll();

	//THEN
	assertEquals(list.contains(fireStation), true);
	
    }
    
}
