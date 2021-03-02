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
public class CommunityEmailServiceControllerIT {
    
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Test que l'on reçoit les bonnes valeurs avec une ville valide")
    public void getEmailPersonsInTheCityTest() throws Exception {
	mockMvc.perform(get("/communityEmail?city=Culver"))
		.andExpect(jsonPath("$.email[0]").value("jaboyd@email.com"))
		.andExpect(jsonPath("$.email[14]").value("gramps@email.com"));
    }
    
    @Test
    @DisplayName("Test que l'on ne reçoit pas de valeur avec une ville invalide")
    public void getEmailPersonsInTheInvalidCityTest() throws Exception {
	mockMvc.perform(get("/communityEmail?city=False"))
		.andExpect(jsonPath("$.email").doesNotExist());
    }

}
