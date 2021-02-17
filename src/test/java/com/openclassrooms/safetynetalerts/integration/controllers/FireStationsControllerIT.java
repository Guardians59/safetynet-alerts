package com.openclassrooms.safetynetalerts.integration.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

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
import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.PutFireStationsModel;
import com.openclassrooms.safetynetalerts.services.impl.FireStationsServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationsControllerIT {
    
    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    FireStationsServiceImpl fireStationsServiceImpl;
    
    @Test
    @DisplayName("Test de l'ajout d'une station")
    public void addNewFireStationTest() throws Exception {
	//GIVEN
	FireStationsModel station = new FireStationsModel();
	station.setAddress("24 haute rue");
	station.setStation(6);
	Gson gson = new Gson();
	String json = gson.toJson(station);
	
	//WHEN
	when(fireStationsServiceImpl.saveNewFireStation(station)).thenReturn(true);
	
	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.post("/firestation/add")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

    }
    
    @Test
    @DisplayName("Test no content lors de l'ajout d'une station")
    public void addNewFireStationErrorTest() throws Exception {
	//GIVEN
	FireStationsModel station = new FireStationsModel();
	station.setAddress("24 haute rue");
	Gson gson = new Gson();
	String json = gson.toJson(station);
	
	//WHEN
	when(fireStationsServiceImpl.saveNewFireStation(station)).thenReturn(false);

	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.post("/firestation/add")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());

    }
    
    @Test
    @DisplayName("Test no content lors de l'ajout d'une station déjà présente")
    public void addFireStationErrorTest() throws Exception {
	//GIVEN
	FireStationsModel station = new FireStationsModel();
	station.setAddress("892 Downing Ct");
	station.setStation(2);
	Gson gson = new Gson();
	String json = gson.toJson(station);
	
	//WHEN
	when(fireStationsServiceImpl.saveNewFireStation(station)).thenReturn(false);

	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.post("/firestation/add")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());

    }
    
    @Test
    @DisplayName("Test de la mis à jour d'une station")
    public void updateFireStationTest() throws Exception {
	//GIVEN
	PutFireStationsModel fireStation = new PutFireStationsModel();
	fireStation.setAddress("1509 Culver St");
	fireStation.setNewStationNumber(6);
	fireStation.setOldStationNumber(3);
	Gson gson = new Gson();
	String json = gson.toJson(fireStation);
	
	//WHEN
	when(fireStationsServiceImpl.updateFireStation(fireStation)).thenReturn(true);
	
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.put("/firestation/update")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

    }
    
    @Test
    @DisplayName("Test not found lors de la mis à jour d'une station")
    public void updateFireStationErrorTest() throws Exception {
	//GIVEN
	PutFireStationsModel station = new PutFireStationsModel();
	station.setAddress("951 LoneTree Rd false");
	station.setOldStationNumber(2);
	station.setNewStationNumber(5);
	Gson gson = new Gson();
	String json = gson.toJson(station);
	
	//WHEN
	when(fireStationsServiceImpl.updateFireStation(station)).thenReturn(false);

	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.put("/firestation/update")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());

    }
    
    @Test
    @DisplayName("Test de la suppression d'une station")
    public void deleteFireStationTest() throws Exception {
	//GIVEN
	Optional<Integer> station = Optional.of(1);
	Optional<String> address = Optional.of("644 Gershwin Cir") ;
	String stationParam = "1";
	String addressParam = "644 Gershwin Cir";
	
	//WHEN
	when(fireStationsServiceImpl.deleteFireStation(station, address)).thenReturn(true);
	
	//THEN
	mockMvc.perform(delete("/firestation/delete")
		.param("station", stationParam)
		.param("address", addressParam))
		.andExpect(status().isOk());

    }
    
    @Test
    @DisplayName("Test de la suppression d'une adresse de station")
    public void deleteFireStationAddressTest() throws Exception {
	//GIVEN
	Optional<Integer> station = Optional.empty();
	Optional<String> address = Optional.of("489 Manchester St");
	String addressParam = "489 Manchester St";
	
	//WHEN
	when(fireStationsServiceImpl.deleteFireStation(station, address)).thenReturn(true);
	
	//THEN
	mockMvc.perform(delete("/firestation/delete")
		.param("address", addressParam))
		.andExpect(status().isOk());

    }
    
    @Test
    @DisplayName("Test de la suppression d'une station par son numéro")
    public void deleteFireStationNumberTest() throws Exception {
	//GIVEN
	Optional<Integer> station = Optional.of(1);
	Optional<String> address = Optional.empty();
	String stationParam = "1";
	
	//WHEN
	when(fireStationsServiceImpl.deleteFireStation(station, address)).thenReturn(true);
	
	//THE
	mockMvc.perform(delete("/firestation/delete")
		.param("station", stationParam))
		.andExpect(status().isOk());

    }
    
    @Test
    @DisplayName("Test not found lors de la suppression d'une station")
    public void deleteFireStationErrorTest() throws Exception {
	//GIVEN
	Optional<Integer> station = Optional.of(4);
	Optional<String> address = Optional.of("112 Steppes Pl false");
	String stationParam = "4";
	String addressParam = "112 Steppes Pl false";
	
	//WHEN
	when(fireStationsServiceImpl.deleteFireStation(station, address)).thenReturn(false);
	
	//THEN
	mockMvc.perform(delete("/firestation/delete")
		.param("station", stationParam)
		.param("address", addressParam))
		.andExpect(status().isNotFound());

    }

}
