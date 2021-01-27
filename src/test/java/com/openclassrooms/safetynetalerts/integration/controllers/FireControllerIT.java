package com.openclassrooms.safetynetalerts.integration.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class FireControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Test que l'on reçoit les bonnes valeurs pour une adresse valide")
    public void getPersonsListAndStationNumberAtTheAddressTest() throws Exception {
	mockMvc.perform(get("/fire?address=112 Steppes Pl"))
		.andExpect(jsonPath("$.persons[0].lastName").value("Cooper"))
		.andExpect(jsonPath("$.persons[0].phoneNumber").value("841-874-6874"))
		.andExpect(jsonPath("$.persons[0].medications[0]").value("hydrapermazol:300mg"))
		.andExpect(jsonPath("$.persons[0].allergies[0]").value("shellfish"))

		.andExpect(jsonPath("$.stationNumber[0]").value("3"))
		.andExpect(jsonPath("$.stationNumber[1]").value("4"));
    }

    @Test
    @DisplayName("Test que l'on reçoit aucune valeur pour une adresse invalide")
    public void getPersonsListAndStationNumberAtTheInvalidAddressTest() throws Exception {
	mockMvc.perform(get("/fire?address=false"))
		.andExpect(jsonPath("$.persons").doesNotExist())

		.andExpect(jsonPath("$.stationNumber").doesNotExist());

    }
}
