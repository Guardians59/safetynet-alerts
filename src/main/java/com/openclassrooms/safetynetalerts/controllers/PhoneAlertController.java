package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.services.impl.PhoneAlertServiceImpl;

@RestController
public class PhoneAlertController {

    @Autowired
    PhoneAlertServiceImpl phoneAlertServiceImpl;

    @GetMapping(value = "phoneAlert")
    public HashMap<String, Object> getListPhoneNumbersPersonFindByTheStationNumber(
	    @RequestParam(value = "firestation") int station) throws IOException {

	return phoneAlertServiceImpl.findPhoneNumberPersonByTheStationNumber(station);
    }

}
