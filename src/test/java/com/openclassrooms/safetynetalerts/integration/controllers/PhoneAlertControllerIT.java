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
public class PhoneAlertControllerIT {
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @DisplayName("Test que l'on reçoit les bonnes valeurs avec un numéro de station valide")
    public void getPhoneNumbersPersonsByStationNumber1Test() throws Exception {
	mockMvc.perform(get("/phoneAlert?firestation=1"))
		.andExpect(jsonPath("$.phoneNumbers[0]").value("841-874-6512"))
		.andExpect(jsonPath("$.phoneNumbers[3]").value("841-874-7784"));
	
    }
    
    @Test
    @DisplayName("Test que l'on reçoit les bonnes valeurs sans doublon pour la station N°3")
    public void getPhoneNumbersPersonsByStationNumber3Test() throws Exception {
	mockMvc.perform(get("/phoneAlert?firestation=3"))
		.andExpect(jsonPath("$.phoneNumbers[0]").value("841-874-6512"))
		.andExpect(jsonPath("$.phoneNumbers[6]").value("841-874-9888"));
	
    }
    
    @Test
    @DisplayName("Test que l'on reçoit aucune valeur avec un numéro de station invalide")
    public void getPhoneNumbersPersonsByInvalidStationNumberTest() throws Exception {
	mockMvc.perform(get("/phoneAlert?firestation=10"))
		.andExpect(jsonPath("$.phoneNumbers").doesNotExist());
		
    }

}
