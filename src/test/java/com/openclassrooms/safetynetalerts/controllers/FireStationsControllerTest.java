package com.openclassrooms.safetynetalerts.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Test que l'url renvoie une réponse avec un numéro de station valide")
    public void getPersonsByStationNumber1Test() throws Exception {
	mockMvc.perform(get("/firestation?stationNumber=1")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test que l'url ne reçoit pas de réponse avec un numéro de station invalide")
    public void getPersonsByStationNumberErrorTest() throws Exception {
	mockMvc.perform(get("/firestation?stationNumber=e"))
		.andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Test que l'url renvoie une réponse avec uniquement le param station")
    public void deleteFireStationStationTest() throws Exception {
	mockMvc.perform(delete("/firestation/delete?station=1"))
		.andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Test que l'url renvoie une réponse avec uniquement le param adresse")
    public void deleteFireStationAddressTest() throws Exception {
	mockMvc.perform(delete("/firestation/delete?address=1509 Culver St"))
		.andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Test que l'url renvoie une réponse avec les deux paramètres valides")
    public void deleteFireStationTest() throws Exception {
	mockMvc.perform(delete("/firestation/delete?station=2&address=29 15th St"))
		.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test que l'url delete reçoit badRequest en cas de param erroné")
    public void deleteFireStationStationNumberErrorTest() throws Exception {
	mockMvc.perform(delete("/firestation/delete?station=e"))
		.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test que l'url delete reçoit badRequest quand au moins un des deux param est erroné")
    public void deleteFireStationErrorTest() throws Exception {
	mockMvc.perform(delete("/firestation/delete?station=e&address=1509 Culver St"))
		.andExpect(status().isBadRequest());
    }

}
