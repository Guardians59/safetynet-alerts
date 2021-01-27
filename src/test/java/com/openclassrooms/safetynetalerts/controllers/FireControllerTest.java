package com.openclassrooms.safetynetalerts.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class FireControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @DisplayName("Test que l'url est bien exécutée")
    public void getPersonsAndFireStationAtTheAddressTest() throws Exception {
	mockMvc.perform(get("/fire?address=951 LoneTree Rd")).andExpect(status().isOk());
    }
}
