package com.openclassrooms.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.services.impl.PersonInfoServiceImpl;

@SpringBootTest
public class PersonInfoServiceTest {

    @Autowired
    PersonInfoServiceImpl personInfoServiceImpl;

    @Test
    @DisplayName("Test que l'on obtient un résultat avec un nom valide")
    public void getValidPersonInfoTest() throws IOException, ParseException {
	// GIVEN
	HashMap<String, Object> result = new HashMap<>();
	boolean expected = true;

	// WHEN
	result = personInfoServiceImpl.findPersonInfoByName("Peter", "Duncan");

	// THEN
	assertEquals(result.containsKey("personInfo"), expected);

    }

    @Test
    @DisplayName("Test que l'on obtient pas de résultat en cas de nom invalide")
    public void getErrorInvalidPersonInfoTest() throws IOException, ParseException {
	// GIVEN
	HashMap<String, Object> result = new HashMap<>();
	boolean expected = false;

	// WHEN
	result = personInfoServiceImpl.findPersonInfoByName("John", "Boy");

	// THEN
	assertEquals(result.containsKey("personInfo"), expected);
	assertEquals(result.isEmpty(), true);
    }
}
