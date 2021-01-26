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
public class PhoneAlertControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @DisplayName("Test que l'url est bien exécutée")
    public void getPhoneNumbersPersonsByStationNumberTest() throws Exception {
	mockMvc.perform(get("/phoneAlert?firestation=1")).andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Test que l'url n'est pas exécutée si on saisie pas un numéro en paramètre")
    public void getPhoneNumbersPersonsByInvalidStationNumberTest() throws Exception {
	mockMvc.perform(get("/phoneAlert?firestation=a")).andExpect(status().isBadRequest());
    }

}
