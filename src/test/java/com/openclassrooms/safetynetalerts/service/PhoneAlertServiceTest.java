package com.openclassrooms.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.services.impl.PhoneAlertServiceImpl;

@SpringBootTest
public class PhoneAlertServiceTest {

    @Autowired
    PhoneAlertServiceImpl phoneAlertServiceImpl;

    @Test
    @DisplayName("Test que l'on obtient un résultat quand le numéro de station est correct")
    public void getListPhoneNumberPersonByTheStationNumberTest() throws IOException {

	// GIVEN
	HashMap<String, Object> result = new HashMap<>();
	boolean expected = true;

	// WHEN
	result = phoneAlertServiceImpl.findPhoneNumberPersonByTheStationNumber(1);

	// THEN
	assertEquals(result.containsKey("phoneNumbers"), expected);
    }

    @Test
    @DisplayName("Test que l'on obtient pas de résultat pour un numéro de station invalide")
    public void getListPhoneNumberPersonByTheStationNumberErrorTest() throws IOException {
	// GIVEN
	HashMap<String, Object> result = new HashMap<>();
	boolean expected = false;

	// WHEN
	result = phoneAlertServiceImpl.findPhoneNumberPersonByTheStationNumber(5);

	// THEN
	assertEquals(result.containsKey("phoneNumbers"), expected);
    }
}
