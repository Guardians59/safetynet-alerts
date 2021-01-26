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
public class ChildAlertControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Test que l'on reçoit les bonnes valeurs pour une adresse où il y a des enfants")
    public void getListChildrenAtAddress1509CulverStTest() throws Exception {
	mockMvc.perform(get("/childAlert?address=1509 Culver St"))
		.andExpect(jsonPath("$.childrenLivingAtThisAddress[0].firstName").value("Tenley"))
		.andExpect(jsonPath("$.childrenLivingAtThisAddress[0].lastName").value("Boyd"))
		.andExpect(jsonPath("$.childrenLivingAtThisAddress[1].firstName").value("Roger"))
		.andExpect(jsonPath("$.childrenLivingAtThisAddress[1].lastName").value("Boyd"))

		.andExpect(jsonPath("$.personMajorLivingAtThisAddress[0].firstName").value("John"))
		.andExpect(jsonPath("$.personMajorLivingAtThisAddress[0].lastName").value("Boyd"))
		.andExpect(jsonPath("$.personMajorLivingAtThisAddress[2].firstName").value("Felicia"))
		.andExpect(jsonPath("$.personMajorLivingAtThisAddress[2].lastName").value("Boyd"));
    }

    @Test
    @DisplayName("Test que l'on reçoit aucune valeur pour une adresse où il n'y a pas d'enfant")
    public void getListChildrenAtAddress2915thStTest() throws Exception {
	mockMvc.perform(get("/childAlert?address=29 15th St"))
		.andExpect(jsonPath("$.childrenLivingAtThisAddress").doesNotExist())

		.andExpect(jsonPath("$.personMajorLivingAtThisAddress").doesNotExist());

    }

    @Test
    @DisplayName("Test que l'on reçoit aucune valeur pour une adresse invalide")
    public void getListChildrenAtInvalidAddressTest() throws Exception {
	mockMvc.perform(get("/childAlert?address=false"))
		.andExpect(jsonPath("$.childrenLivingAtThisAddress").doesNotExist())

		.andExpect(jsonPath("$.personMajorLivingAtThisAddress").doesNotExist());

    }

}
