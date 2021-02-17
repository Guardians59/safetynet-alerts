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
public class MedicalRecordsControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @DisplayName("Test que l'url get est bien exécutée")
    public void getPersonsListTest() throws Exception {
	mockMvc.perform(get("/medicalRecord/get")).andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Test que l'url delete renvoie une réponse avec des paramètres valides")
    public void deleteMedicalRecordTest() throws Exception {
	mockMvc.perform(delete("/medicalRecord/delete?firstName=Tenley&lastName=Boyd"))
		.andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Test que l'url delete reçoit badRequest en cas de param erroné")
    public void deleteMedicalRecordErrorTest() throws Exception {
	mockMvc.perform(delete("/medicalRecord/delete?lastName=Boyd"))
		.andExpect(status().isBadRequest());
    }

}
