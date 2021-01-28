package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.services.impl.PersonInfoServiceImpl;

@RestController
public class PersonInfoController {

    @Autowired
    PersonInfoServiceImpl personInfoServiceImpl;

    @GetMapping(value = "personInfo")
    public HashMap<String, Object> getPersonInfoByName(@RequestParam(value = "firstName") String firstName,
	    @RequestParam(value = "lastName") String lastName) throws IOException, ParseException {

	return personInfoServiceImpl.findPersonInfoByName(firstName, lastName);
    }

}
