package com.openclassrooms.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

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

	// WHEN
	medicalRecords = medicalRecordsServiceImpl.findAll().get(22);

	// THEN
	assertEquals(expectedLastFirstName, medicalRecords.getFirstName());
	assertEquals(expectedLastLastName, medicalRecords.getLastName());
	assertEquals(expectedLastBirthdate, medicalRecords.getBirthdate());
    }

}
