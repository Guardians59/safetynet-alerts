package com.openclassrooms.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.services.impl.PersonsServiceImpl;

@SpringBootTest
public class CommunityEmailTest {

    @Autowired
    PersonsServiceImpl personsServiceImpl;

    @Test
    @DisplayName("Test que la liste n'est pas null")
    public void getPersonsMailInTheCityTest() throws IOException {
	// GIVEN
	String email;

	// WHEN
	email = personsServiceImpl.findEmail("Culver").get(0);

	// THEN
	assertNotNull(email);
    }

    @Test
    @DisplayName("Test que tous les Emails soient bien présent")
    public void verifySizeListEmailTest() throws IOException {
	// GIVEN
	int numberEmail;

	// WHEN
	numberEmail = personsServiceImpl.findEmail("Culver").size();

	// THEN
	assertEquals(numberEmail, 23);
    }

}
