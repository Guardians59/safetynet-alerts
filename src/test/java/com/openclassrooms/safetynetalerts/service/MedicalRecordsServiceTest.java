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

import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.services.impl.MedicalRecordsServiceImpl;

@SpringBootTest
public class MedicalRecordsServiceTest {

    @Autowired
    private MedicalRecordsServiceImpl medicalRecordsServiceImpl;

    @Test
    @DisplayName("Test que les attributs ne renvoient pas null")
    public void getMedicalRecordsTest() throws IOException {
	// GIVEN
	MedicalRecordsModel medicalRecords = new MedicalRecordsModel();

	// WHEN
	medicalRecords = medicalRecordsServiceImpl.findAll().get(0);

	// THEN
	assertNotNull(medicalRecords.getFirstName());
	assertNotNull(medicalRecords.getLastName());
	assertNotNull(medicalRecords.getBirthdate());
	assertNotNull(medicalRecords.getAllergies());
	assertNotNull(medicalRecords.getMedications());
    }

    @Test
    @DisplayName("Test que l'on récupére la liste jusqu'au dernier antécédent médical du JSON")
    public void getLastMedicalRecordsTest() throws IOException {
	// GIVEN
	MedicalRecordsModel medicalRecords = new MedicalRecordsModel();
	String expectedLastFirstName = "Eric";
	String expectedLastLastName = "Cadigan";
	String expectedLastBirthdate = "08/06/1945";
	int size = medicalRecordsServiceImpl.findAll().size();

	// WHEN
	medicalRecords = medicalRecordsServiceImpl.findAll().get(size - 1);

	// THEN
	assertEquals(expectedLastFirstName, medicalRecords.getFirstName());
	assertEquals(expectedLastLastName, medicalRecords.getLastName());
	assertEquals(expectedLastBirthdate, medicalRecords.getBirthdate());
    }

    @Test
    @DisplayName("Test de l'ajout d'un medical record")
    public void addNewMedicalRecordTest() throws IOException {
	//GIVEN
	List<MedicalRecordsModel> list = new ArrayList<>();
	MedicalRecordsModel medicalRecords = new MedicalRecordsModel();
	medicalRecords.setFirstName("Rick");
	medicalRecords.setLastName("Stones");
	medicalRecords.setBirthdate("10/24/1990");
	List<String> medications = new ArrayList<>();
	medications.add("flecainide");
	medicalRecords.setMedications(medications);
	List<String> allergies = new ArrayList<>();
	medicalRecords.setAllergies(allergies);

	//WHEN
	medicalRecordsServiceImpl.saveNewMedicalRecords(medicalRecords);
	list = medicalRecordsServiceImpl.findAll();

	//THEN
	assertEquals(list.contains(medicalRecords), true);
    }
    
    @Test
    @DisplayName("Test de l'ajout d'un medical record déjà présent")
    public void addMedicalRecordTest() throws IOException {
	//GIVEN
	List<MedicalRecordsModel> list = new ArrayList<>();
	MedicalRecordsModel medicalRecords = new MedicalRecordsModel();
	medicalRecords.setFirstName("Foster");
	medicalRecords.setLastName("Shepard");
	medicalRecords.setBirthdate("01/08/1980");
	List<String> medications = new ArrayList<>();
	medications.add("flecainide");
	medicalRecords.setMedications(medications);
	List<String> allergies = new ArrayList<>();
	medicalRecords.setAllergies(allergies);
	int numberOfMedicalRecords = 0;

	//WHEN
	medicalRecordsServiceImpl.saveNewMedicalRecords(medicalRecords);
	list = medicalRecordsServiceImpl.findAll();
	Iterator<MedicalRecordsModel> iterator = list.iterator();
	while(iterator.hasNext()) {
	    MedicalRecordsModel medicalRecordsIterator = new MedicalRecordsModel();
	    medicalRecordsIterator = iterator.next();
	    if(medicalRecordsIterator.getFirstName().equals(medicalRecords.getFirstName()) && 
		    medicalRecordsIterator.getLastName().equals(medicalRecords.getLastName())) {
		numberOfMedicalRecords++;
	    }
	}

	//THEN
	assertEquals(numberOfMedicalRecords, 1);
    }

    @Test
    @DisplayName("Test de l'ajout d'un medical record non valide, information manquante")
    public void addNewMedicalRecordErrorTest() throws IOException {
	//GIVEN
	List<MedicalRecordsModel> list = new ArrayList<>();
	MedicalRecordsModel medicalRecords = new MedicalRecordsModel();
	medicalRecords.setFirstName("Rick");
	medicalRecords.setLastName("Stones");
	List<String> medications = new ArrayList<>();
	medications.add("flecainide");
	medicalRecords.setMedications(medications);
	List<String> allergies = new ArrayList<>();
	medicalRecords.setAllergies(allergies);

	//WHEN
	medicalRecordsServiceImpl.saveNewMedicalRecords(medicalRecords);
	list = medicalRecordsServiceImpl.findAll();

	//THEN
	assertEquals(list.contains(medicalRecords), false);
    }

    @Test
    @DisplayName("Test de la mis à jour d'un medical record")
    public void updateMedicalRecordTest() throws IOException {
	//GIVEN
	List<MedicalRecordsModel> list = new ArrayList<>();
	MedicalRecordsModel medicalRecords = new MedicalRecordsModel();
	medicalRecords.setFirstName("Clive");
	medicalRecords.setLastName("Ferguson");
	medicalRecords.setBirthdate("03/06/1995");
	List<String> medications = new ArrayList<>();
	medications.add("flecainide");
	medicalRecords.setMedications(medications);
	List<String> allergies = new ArrayList<>();
	medicalRecords.setAllergies(allergies);
	MedicalRecordsModel oldMedicalRecords = new MedicalRecordsModel();
	oldMedicalRecords.setFirstName("Clive");
	oldMedicalRecords.setLastName("Ferguson");
	oldMedicalRecords.setBirthdate("03/06/1994");
	List<String> oldMedications = new ArrayList<>();
	oldMedicalRecords.setMedications(oldMedications);
	List<String> oldAllergies = new ArrayList<>();
	oldMedicalRecords.setAllergies(oldAllergies);

	//WHEN
	medicalRecordsServiceImpl.updateMedicalRecords(medicalRecords);
	list = medicalRecordsServiceImpl.findAll();

	//THEN
	assertEquals(list.contains(medicalRecords), true);
	assertEquals(list.contains(oldMedicalRecords), false);
    }
    
    @Test
    @DisplayName("Test tentative de mis à jour d'un medical record avec les mêmes informations")
    public void updateMedicalRecordWithSameInfoTest() throws IOException {
	//GIVEN
	List<MedicalRecordsModel> list = new ArrayList<>();
	MedicalRecordsModel medicalRecords = new MedicalRecordsModel();
	medicalRecords.setFirstName("Sophia");
	medicalRecords.setLastName("Zemicks");
	medicalRecords.setBirthdate("03/06/1988");
	List<String> medications = new ArrayList<>();
	medications.add("aznol:60mg");
	medications.add("hydrapermazol:900mg");
	medications.add("pharmacol:5000mg");
	medications.add("terazine:500mg");
	medicalRecords.setMedications(medications);
	List<String> allergies = new ArrayList<>();
	medicalRecords.setAllergies(allergies);
	int numberOfMedicalRecords = 0;

	//WHEN
	medicalRecordsServiceImpl.updateMedicalRecords(medicalRecords);
	list = medicalRecordsServiceImpl.findAll();
	Iterator<MedicalRecordsModel> iterator = list.iterator();
	while(iterator.hasNext()) {
	    MedicalRecordsModel medicalRecordsIterator = new MedicalRecordsModel();
	    medicalRecordsIterator = iterator.next();
	    if(medicalRecordsIterator.getFirstName().equals(medicalRecords.getFirstName()) && 
		    medicalRecordsIterator.getLastName().equals(medicalRecords.getLastName())) {
		numberOfMedicalRecords++;
	    }
	}

	//THEN
	assertEquals(list.contains(medicalRecords), true);
	assertEquals(numberOfMedicalRecords, 1);
	
    }

    @Test
    @DisplayName("Test de la mis à jour d'un medical record non présent dans la liste")
    public void updateMedicalRecordErrorTest() throws IOException {
	//GIVEN
	List<MedicalRecordsModel> list = new ArrayList<>();
	MedicalRecordsModel medicalRecords = new MedicalRecordsModel();
	medicalRecords.setFirstName("John");
	medicalRecords.setLastName("Boydd");
	medicalRecords.setBirthdate("10/24/1990");
	List<String> medications = new ArrayList<>();
	medications.add("flecainide");
	medicalRecords.setMedications(medications);
	List<String> allergies = new ArrayList<>();
	medicalRecords.setAllergies(allergies);

	//WHEN
	medicalRecordsServiceImpl.updateMedicalRecords(medicalRecords);
	list = medicalRecordsServiceImpl.findAll();

	//THEN
	assertEquals(list.contains(medicalRecords), false);
    }

    @Test
    @DisplayName("Test de la suppression d'un medical record")
    public void deleteMedicalRecordTest() throws IOException {
	//GIVEN
	List<MedicalRecordsModel> list = new ArrayList<>();
	String firstName = "John";
	String lastName = "Boyd";

	//WHEN
	medicalRecordsServiceImpl.deleteMedicalRecords(firstName, lastName);
	list = medicalRecordsServiceImpl.findAll();

	//THEN
	assertNotEquals(list.get(0).getFirstName(), "John");
	assertNotEquals(list.get(0).getBirthdate(), "03/06/1984");

    }

    @Test
    @DisplayName("Test de la tentative de suppression d'un medical record non présent dans la liste")
    public void deleteMedicalRecordErrorTest() throws IOException {
	//GIVEN
	List<MedicalRecordsModel> list = new ArrayList<>();
	String firstName = "John";
	String lastName = "Boydd";

	//WHEN
	medicalRecordsServiceImpl.deleteMedicalRecords(firstName, lastName);
	list = medicalRecordsServiceImpl.findAll();

	//THEN
	assertEquals(list.get(0).getFirstName(), "John");
	assertEquals(list.get(0).getBirthdate(), "03/06/1984");

    }

}
