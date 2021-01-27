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
public class FloodControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Test que l'on reçoit les bonnes valeurs avec des numéros de station valides")
    public void getHomesServedByTheStationTest() throws Exception {
	mockMvc.perform(get("/flood/stations?stations=3,4"))
		.andExpect(jsonPath("$.stations[0].stationNumber").value("3"))
		.andExpect(jsonPath("$.stations[0].listHomes[0].address").value("1509 Culver St"))
		.andExpect(jsonPath("$.stations[0].listHomes[0].listPersonsInHome[0].lastName").value("Boyd"))
		.andExpect(
			jsonPath("$.stations[0].listHomes[0].listPersonsInHome[0].phoneNumber").value("841-874-6512"))
		.andExpect(
			jsonPath("$.stations[0].listHomes[0].listPersonsInHome[0].medications[0]").value("aznol:350mg"))
		.andExpect(jsonPath("$.stations[0].listHomes[0].listPersonsInHome[0].allergies[0]").value("nillacilan"))
		.andExpect(
			jsonPath("$.stations[0].listHomes[0].listPersonsInHome[4].phoneNumber").value("841-874-6544"))
		.andExpect(jsonPath("$.stations[0].listHomes[0].listPersonsInHome[4].medications[0]")
			.value("tetracyclaz:650mg"))
		.andExpect(jsonPath("$.stations[0].listHomes[0].listPersonsInHome[4].allergies[0]").value("xilliathal"))
		.andExpect(jsonPath("$.stations[0].listHomes[0].listPersonsInHome[5]").doesNotExist())

		.andExpect(jsonPath("$.stations[0].listHomes[1].address").value("834 Binoc Ave"))
		.andExpect(jsonPath("$.stations[0].listHomes[2].address").value("748 Townings Dr"))
		.andExpect(jsonPath("$.stations[0].listHomes[3].address").value("112 Steppes Pl"))
		.andExpect(jsonPath("$.stations[0].listHomes[4].address").doesNotExist())

		.andExpect(jsonPath("$.stations[1].stationNumber").value("4"))
		.andExpect(jsonPath("$.stations[1].listHomes[0].address").value("489 Manchester St"))
		.andExpect(jsonPath("$.stations[1].listHomes[1].address").value("112 Steppes Pl"));
    }

    @Test
    @DisplayName("Test que l'on reçoit simplement les valeurs des numéros invalides")
    public void getValidHomesServedByTheStationTest() throws Exception {
	mockMvc.perform(get("/flood/stations?stations=4,6,7,3"))
		.andExpect(jsonPath("$.stations[0].stationNumber").value("4"))
		.andExpect(jsonPath("$.stations[1].stationNumber").value("3"))
		.andExpect(jsonPath("$.stations[2].stationNumber").doesNotExist());
    }
    
    @Test
    @DisplayName("Test que l'on reçoit pas de valeur avec des numéros invalides")
    public void getErrorHomesServedByTheStationTest() throws Exception {
	mockMvc.perform(get("/flood/stations?stations=6,7"))
		.andExpect(jsonPath("$.stations").doesNotExist());
    }
}