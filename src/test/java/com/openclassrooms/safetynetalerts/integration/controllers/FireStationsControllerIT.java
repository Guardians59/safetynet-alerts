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
public class FireStationsControllerIT {
    
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Test que l'on reçoit les bonnes valeurs pour la station N°1")
    public void getPersonsListByStationNumber1Test() throws Exception {
	mockMvc.perform(get("/firestation?stationNumber=1"))
		.andExpect(jsonPath("$.numberOfMinorPerson").value("1"))
		.andExpect(jsonPath("$.numberOfMajorPerson").value("5"))
	
		.andExpect(jsonPath("$.personsMajor[0].firstName").value("Peter"))
		.andExpect(jsonPath("$.personsMajor[0].lastName").value("Duncan"))
		.andExpect(jsonPath("$.personsMajor[4].firstName").value("Shawna"))
		.andExpect(jsonPath("$.personsMajor[4].lastName").value("Stelzer"))
		.andExpect(jsonPath("$.personsMinor[0].firstName").value("Kendrik"))
		.andExpect(jsonPath("$.personsMinor[0].lastName").value("Stelzer"));
	
    }
    
    @Test
    @DisplayName("Test que l'on reçoit les bonnes valeurs pour la station N°2")
    public void getPersonsListByStationNumber2Test() throws Exception {
	mockMvc.perform(get("/firestation?stationNumber=2"))
		.andExpect(jsonPath("$.numberOfMinorPerson").value("1"))
		.andExpect(jsonPath("$.numberOfMajorPerson").value("4"))
	
		.andExpect(jsonPath("$.personsMajor[0].firstName").value("Jonanathan"))
		.andExpect(jsonPath("$.personsMajor[0].lastName").value("Marrack"))
		.andExpect(jsonPath("$.personsMajor[3].firstName").value("Eric"))
		.andExpect(jsonPath("$.personsMajor[3].lastName").value("Cadigan"))
		.andExpect(jsonPath("$.personsMinor[0].firstName").value("Zach"))
		.andExpect(jsonPath("$.personsMinor[0].lastName").value("Zemicks"));
	
    }
    
    @Test
    @DisplayName("Test que l'on reçoit les bonnes valeurs pour la station N°3")
    public void getPersonsListByStationNumber3Test() throws Exception {
	mockMvc.perform(get("/firestation?stationNumber=3"))
		.andExpect(jsonPath("$.numberOfMinorPerson").value("3"))
		.andExpect(jsonPath("$.numberOfMajorPerson").value("8"))
	
		.andExpect(jsonPath("$.personsMajor[0].firstName").value("John"))
		.andExpect(jsonPath("$.personsMajor[0].lastName").value("Boyd"))
		.andExpect(jsonPath("$.personsMajor[7].firstName").value("Allison"))
		.andExpect(jsonPath("$.personsMajor[7].lastName").value("Boyd"))
		.andExpect(jsonPath("$.personsMinor[0].firstName").value("Tenley"))
		.andExpect(jsonPath("$.personsMinor[0].lastName").value("Boyd"))
		.andExpect(jsonPath("$.personsMinor[2].firstName").value("Tessa"))
		.andExpect(jsonPath("$.personsMinor[2].lastName").value("Carman"));
	
    }
    
    @Test
    @DisplayName("Test que l'on reçoit les bonnes valeurs pour la station N°4")
    public void getPersonsListByStationNumber4Test() throws Exception {
	mockMvc.perform(get("/firestation?stationNumber=4"))
		.andExpect(jsonPath("$.numberOfMinorPerson").value("0"))
		.andExpect(jsonPath("$.numberOfMajorPerson").value("4"))
	
		.andExpect(jsonPath("$.personsMajor[0].firstName").value("Lily"))
		.andExpect(jsonPath("$.personsMajor[0].lastName").value("Cooper"))
		.andExpect(jsonPath("$.personsMajor[3].firstName").value("Allison"))
		.andExpect(jsonPath("$.personsMajor[3].lastName").value("Boyd"));
	
    }
    
    @Test
    @DisplayName("Test que l'on reçoit aucune réponse avec un numéro de station invalide")
    public void getPersonsListByStationNumber5Test() throws Exception {
	mockMvc.perform(get("/firestation?stationNumber=5"))
		.andExpect(jsonPath("\"$.personsMajor").doesNotExist())
		.andExpect(jsonPath("\"$.personsMinor").doesNotExist());
		
	
    }

}
