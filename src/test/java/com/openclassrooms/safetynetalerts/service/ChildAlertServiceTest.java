package com.openclassrooms.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.services.impl.ChildAlertServiceImpl;

@SpringBootTest
public class ChildAlertServiceTest {

    @Autowired
    ChildAlertServiceImpl childAlertServiceImpl;

    @Test
    @DisplayName("Test qu'une adresse valide où il y a un enfant renvoit une liste d'enfant")
    public void getListChildTest() throws IOException, ParseException {
	// GIVEN
	HashMap<String, Object> result = new HashMap<>();
	boolean expected = true;

	// WHEN
	result = childAlertServiceImpl.findChildAtAddress("1509 Culver St");

	// THEN
	assertEquals(result.containsKey("childrenLivingAtThisAddress"), expected);
    }

    @Test
    @DisplayName("Test qu'une adresse valide sans enfant ne renvoit rien ")
    public void getListValidAddressButNoChildrenTest() throws IOException, ParseException {
	// GIVEN
	HashMap<String, Object> result = new HashMap<>();
	boolean expected = false;

	// WHEN
	result = childAlertServiceImpl.findChildAtAddress("29 15th St");

	// THEN
	assertEquals(result.containsKey("childrenLivingAtThisAddress"), expected);
	assertEquals(result.containsKey("personMajorLivingAtThisAddress"), expected);
    }

    @Test
    @DisplayName("Test de l'erreur d'une mauvaise adresse")
    public void getErrorInvalidAddressTest() throws IOException, ParseException {
	// GIVEN
	HashMap<String, Object> result = new HashMap<>();
	boolean expected = false;

	// WHEN
	result = childAlertServiceImpl.findChildAtAddress("1509 Culver St False");

	// THEN
	assertEquals(result.containsKey("childrenLivingAtThisAddress"), expected);
	assertEquals(result.containsKey("personMajorLivingAtThisAddress"), expected);

    }
}
