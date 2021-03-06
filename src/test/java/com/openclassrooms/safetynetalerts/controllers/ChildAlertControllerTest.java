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
public class ChildAlertControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @DisplayName("Test que l'url est bien exécutée")
    public void getChildAtTheAddressTest() throws Exception {
	mockMvc.perform(get("/childAlert?address=1509 Culver St"))
		.andExpect(status().isOk());
    }

}
