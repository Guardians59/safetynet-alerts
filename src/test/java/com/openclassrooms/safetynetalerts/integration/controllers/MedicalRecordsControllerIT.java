package com.openclassrooms.safetynetalerts.integration.controllers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;
import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.services.impl.MedicalRecordsServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordsControllerIT {
    
    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    MedicalRecordsServiceImpl medicalRecordsServiceImpl;
    
    @Test
    @DisplayName("Test de l'ajout d'un medical record")
    public void addNewMedicalRecordTest() throws Exception {
	//GIVEN
	MedicalRecordsModel medicalRecord = new MedicalRecordsModel();
	medicalRecord.setFirstName("Rick");
	medicalRecord.setLastName("Stones");
	medicalRecord.setBirthdate("04/24/1989");
	List<String> medications = new ArrayList<>();
	medications.add("flecainide");
	medicalRecord.setMedications(medications);
	List<String> allergies = new ArrayList<>();
	medicalRecord.setAllergies(allergies);
	Gson gson = new Gson();
	String json = gson.toJson(medicalRecord);
	
	//WHEN
	when(medicalRecordsServiceImpl.saveNewMedicalRecords(medicalRecord)).thenReturn(true);

	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.post("/medicalRecord/add")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
    }
    
    @Test
    @DisplayName("Test no content lors de l'ajout d'un medical record erroné")
    public void addNewMedicalRecordErrorTest() throws Exception {
	//GIVEN
	MedicalRecordsModel medicalRecord = new MedicalRecordsModel();
	medicalRecord.setFirstName("Rick");
	medicalRecord.setLastName("Stones");
	List<String> medications = new ArrayList<>();
	medications.add("flecainide");
	medicalRecord.setMedications(medications);
	List<String> allergies = new ArrayList<>();
	medicalRecord.setAllergies(allergies);
	Gson gson = new Gson();
	String json = gson.toJson(medicalRecord);
	
	//WHEN
	when(medicalRecordsServiceImpl.saveNewMedicalRecords(medicalRecord)).thenReturn(false);
	
	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.post("/medicalRecord/add")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
    }
    
    @Test
    @DisplayName("Test no content lors de l'ajout d'un medical record déjà présent")
    public void addMedicalRecordErrorTest() throws Exception {
	//GIVEN
	MedicalRecordsModel medicalRecord = new MedicalRecordsModel();
	medicalRecord.setFirstName("Sophia");
	medicalRecord.setLastName("Zemicks");
	medicalRecord.setBirthdate("03/06/1988");
	List<String> medications = new ArrayList<>();
	medications.add("aznol:60mg");
	medications.add("hydrapermazol:900mg");
	medications.add("pharmacol:5000mg");
	medications.add("terazine:500mg");
	medicalRecord.setMedications(medications);
	List<String> allergies = new ArrayList<>();
	medicalRecord.setAllergies(allergies);
	Gson gson = new Gson();
	String json = gson.toJson(medicalRecord);
	
	//WHEN
	when(medicalRecordsServiceImpl.saveNewMedicalRecords(medicalRecord)).thenReturn(false);
	
	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.post("/medicalRecord/add")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
    }
    
    @Test
    @DisplayName("Test de la mis à jour d'un medical record")
    public void updateMedicalRecordTest() throws Exception {
	//GIVEN
	MedicalRecordsModel medicalRecord = new MedicalRecordsModel();
	medicalRecord.setFirstName("John");
	medicalRecord.setLastName("Boyd");
	medicalRecord.setBirthdate("03/06/1984");
	List<String> medications = new ArrayList<>();
	medications.add("flecainide");
	medicalRecord.setMedications(medications);
	List<String> allergies = new ArrayList<>();
	medicalRecord.setAllergies(allergies);
	Gson gson = new Gson();
	String json = gson.toJson(medicalRecord);
	
	//WHEN
	when(medicalRecordsServiceImpl.updateMedicalRecords(medicalRecord)).thenReturn(true);

	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.put("/medicalRecord/update")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Test de la mis à jour d'un medical record avec les mêmes informations")
    public void updateMedicalRecordWithSameInfoTest() throws Exception {
	//GIVEN
	MedicalRecordsModel medicalRecord = new MedicalRecordsModel();
	medicalRecord.setFirstName("Sophia");
	medicalRecord.setLastName("Zemicks");
	medicalRecord.setBirthdate("03/06/1988");
	List<String> medications = new ArrayList<>();
	medications.add("aznol:60mg");
	medications.add("hydrapermazol:900mg");
	medications.add("pharmacol:5000mg");
	medications.add("terazine:500mg");
	medicalRecord.setMedications(medications);
	List<String> allergies = new ArrayList<>();
	medicalRecord.setAllergies(allergies);
	Gson gson = new Gson();
	String json = gson.toJson(medicalRecord);
	
	//WHEN
	when(medicalRecordsServiceImpl.updateMedicalRecords(medicalRecord)).thenReturn(false);

	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.put("/medicalRecord/update")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("Test not found lors de la mis à jour d'un medical record erroné")
    public void updateMedicalRecordErrorTest() throws Exception {
	//GIVEN
	MedicalRecordsModel medicalRecord = new MedicalRecordsModel();
	medicalRecord.setFirstName("John");
	medicalRecord.setLastName("Boydd");
	medicalRecord.setBirthdate("03/06/1984");
	List<String> medications = new ArrayList<>();
	medications.add("flecainide");
	medicalRecord.setMedications(medications);
	Gson gson = new Gson();
	String json = gson.toJson(medicalRecord);
	
	//WHEN
	when(medicalRecordsServiceImpl.updateMedicalRecords(medicalRecord)).thenReturn(false);
	
	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.put("/medicalRecord/update")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("Test de la suppression d'un medical record")
    public void deleteMedicalRecordTest() throws Exception {
	//GIVEN
	String firstName = "John";
	String lastName = "Boyd";
	
	//WHEN
	when(medicalRecordsServiceImpl.deleteMedicalRecords(firstName, lastName)).thenReturn(true);

	//THEN
	mockMvc.perform(delete("/medicalRecord/delete")
			.param("firstName", firstName)
			.param("lastName", lastName))
			.andExpect(status().isOk());

    }
    
    @Test
    @DisplayName("Test not found lors de la suppression d'un medical record erroné")
    public void deleteMedicalRecordErrorTest() throws Exception {
	//GIVEN
	String firstName = "John";
	String lastName = "Boydd";
	
	//WHEN
	when(medicalRecordsServiceImpl.deleteMedicalRecords(firstName, lastName)).thenReturn(false);

	//THEN
	mockMvc.perform(delete("/medicalRecord/delete")
			.param("firstName", firstName)
			.param("lastName", lastName))
			.andExpect(status().isNotFound());

    }

}
