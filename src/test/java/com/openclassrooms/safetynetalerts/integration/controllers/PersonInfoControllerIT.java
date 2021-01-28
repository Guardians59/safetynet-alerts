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
public class PersonInfoControllerIT {
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @DisplayName("Test que l'ont reçoit les bonnes valeurs avec un nom valide")
    public void getPersonInfoByNameTest() throws Exception {
	mockMvc.perform(get("/personInfo?firstName=John&lastName=Boyd"))
		.andExpect(jsonPath("$.personInfo[0].lastName").value("Boyd"))
		.andExpect(jsonPath("$.personInfo[0].address").value("1509 Culver St"))
		.andExpect(jsonPath("$.personInfo[0].city").value("Culver"))
		.andExpect(jsonPath("$.personInfo[0].zip").value("97451"))
		.andExpect(jsonPath("$.personInfo[0].mail").value("jaboyd@email.com"))
		.andExpect(jsonPath("$.personInfo[0].medications[0]").value("aznol:350mg"))
		.andExpect(jsonPath("$.personInfo[0].allergies[0]").value("nillacilan"));
    }
    
    @Test
    @DisplayName("Test que l'on ne reçoit pas de valeur avec un prénom incorrect")
    public void getPersonInfoByInvalidFirstNameTest() throws Exception {
	mockMvc.perform(get("/personInfo?firstName=False&lastName=Boyd"))
		.andExpect(jsonPath("$.personInfo").doesNotExist());
		
    }
    
    @Test
    @DisplayName("Test que l'on ne reçoit pas de valeur avec un nom complet incorrect")
    public void getPersonInfoByInvalidNameTest() throws Exception {
	mockMvc.perform(get("/personInfo?firstName=False&lastName=False"))
		.andExpect(jsonPath("$.personInfo").doesNotExist());
		
    }
    
}
