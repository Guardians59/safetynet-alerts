package com.openclassrooms.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.services.impl.CommunityEmailServiceImpl;

@SpringBootTest
public class CommunityEmailServiceTest {

    @Autowired
    CommunityEmailServiceImpl communityEmailServiceImpl;

    @Test
    @DisplayName("Test que l'on obtient un résultat avec une ville valide")
    public void getPersonsMailInTheCityTest() throws IOException {
	// GIVEN
	HashMap<String, Object> result = new HashMap<>();
	boolean expected = true;

	// WHEN
	result = communityEmailServiceImpl.findEmail("Culver");
	
	// THEN
	assertEquals(result.containsKey("email"), expected);
    }

    @Test
    @DisplayName("Test que l'on obtient pas de résultat avec une ville invalide")
    public void getErrorPersonsMailWithInvalidCityTest() throws IOException {
	// GIVEN
	HashMap<String, Object> result = new HashMap<>();
	boolean expected = false;

	// WHEN
	result = communityEmailServiceImpl.findEmail("Culve");

	// THEN
	assertEquals(result.containsKey("email"), expected);
    }

}
