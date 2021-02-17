package com.openclassrooms.safetynetalerts.integration.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.google.gson.Gson;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.services.impl.PersonsServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonsControllerIT {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonsServiceImpl personsServiceImpl;

    @Test
    @DisplayName("Test de l'ajout d'une personne")
    public void addNewPersonTest() throws Exception {
	//GIVEN
	PersonsModel person = new PersonsModel();
	person.setFirstName("Rick");
	person.setLastName("Stones");
	person.setAddress("17 haute rue");
	person.setCity("Culver");
	person.setZip(59242);
	person.setPhone("066-669-2417");
	person.setEmail("rick@mail.com");
	Gson gson = new Gson();
	String json = gson.toJson(person);
	
	//WHEN
	when(personsServiceImpl.saveNewPerson(person)).thenReturn(true);

	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.post("/person/add")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

    }
    
    @Test
    @DisplayName("Test no content lors de l'ajout d'une personne déjà existante")
    public void addPersonTest() throws Exception {
	//GIVEN
	PersonsModel person = new PersonsModel();
	person.setFirstName("Sophia");
 	person.setLastName("Zemicks");
 	person.setAddress("892 Downing Ct");
 	person.setCity("Culver");
 	person.setZip(97451);
 	person.setPhone("841-874-7878");
 	person.setEmail("soph@email.com");
	Gson gson = new Gson();
	String json = gson.toJson(person);
	
	//WHEN
	when(personsServiceImpl.saveNewPerson(person)).thenReturn(false);

	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.post("/person/add")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());

    }
    
    @Test
    @DisplayName("Test no content lors de l'ajout d'une personne erronée")
    public void addNewPersonErrorTest() throws Exception {
	//GIVEN
	PersonsModel person = new PersonsModel();
	person.setFirstName("Rick");
	person.setLastName("Stones");
	person.setAddress("17 haute rue");
	person.setZip(59242);
	person.setPhone("066-669-2417");
	person.setEmail("rick@mail.com");
	Gson gson = new Gson();
	String json = gson.toJson(person);
	
	//WHEN
	when(personsServiceImpl.saveNewPerson(person)).thenReturn(false);

	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.post("/person/add")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());

    }
    
    @Test
    @DisplayName("Test de la mis à jour d'une personne")
    public void updatePersonTest() throws Exception {
	//GIVEN
	PersonsModel person = new PersonsModel();
	person.setFirstName("John");
	person.setLastName("Boyd");
	person.setAddress("17 haute rue");
	person.setCity("Culver");
	person.setZip(59242);
	person.setPhone("066-669-2417");
	person.setEmail("rick@mail.com");
	Gson gson = new Gson();
	String json = gson.toJson(person);
	
	//WHEN
	when(personsServiceImpl.updatePerson(person)).thenReturn(true);

	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.put("/person/update")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

    }
    
    @Test
    @DisplayName("Test not found de la mis à jour d'une personne déjà présente")
    public void updatePersonWithSameInfoTest() throws Exception {
	//GIVEN
	PersonsModel person = new PersonsModel();
	person.setFirstName("Sophia");
 	person.setLastName("Zemicks");
 	person.setAddress("892 Downing Ct");
 	person.setCity("Culver");
 	person.setZip(97451);
 	person.setPhone("841-874-7878");
 	person.setEmail("soph@email.com");
	Gson gson = new Gson();
	String json = gson.toJson(person);
	
	//WHEN
	when(personsServiceImpl.updatePerson(person)).thenReturn(false);

	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.put("/person/update")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());

    }
    
    @Test
    @DisplayName("Test not found lors de la mis à jour d'une personne erronée")
    public void updatePersonErrorTest() throws Exception {
	//GIVEN
	PersonsModel person = new PersonsModel();
	person.setFirstName("Rick");
	person.setLastName("Stones");
	person.setAddress("17 haute rue");
	person.setCity("Culver");
	person.setZip(59242);
	person.setPhone("066-669-2417");
	person.setEmail("rick@mail.com");
	Gson gson = new Gson();
	String json = gson.toJson(person);
	
	//WHEN
	when(personsServiceImpl.updatePerson(person)).thenReturn(false);

	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.put("/person/update")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());

    }
    
    @Test
    @DisplayName("Test de la suppression d'une personne")
    public void deletePersonTest() throws Exception {
	//GIVEN
	String firstName = "John";
	String lastName = "Boyd";
	
	//WHEN
	when(personsServiceImpl.deletePerson(firstName, lastName)).thenReturn(true);

	//THEN
	mockMvc.perform(delete("/person/delete")
			.param("firstName", firstName)
			.param("lastName", lastName))
			.andExpect(status().isOk());

    }
    
    @Test
    @DisplayName("Test not found lors de la suppression d'une personne erronée")
    public void deletePersonErrorTest() throws Exception {
	//GIVEN
	String firstName = "John";
	String lastName = "Boydd";
	
	//WHEN
	when(personsServiceImpl.deletePerson(firstName, lastName)).thenReturn(false);

	//THEN
	mockMvc.perform(delete("/person/delete")
			.param("firstName", firstName)
			.param("lastName", lastName))
			.andExpect(status().isNotFound());

    }
}
