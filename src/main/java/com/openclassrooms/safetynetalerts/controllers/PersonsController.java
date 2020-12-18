package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.services.impl.PersonsServiceImpl;

@RestController
public class PersonsController {
    
    @Autowired PersonsServiceImpl personsServiceImpl;
    
    @GetMapping(value="Persons")
    public List<PersonsModel>getPersonsList() throws IOException {
	return personsServiceImpl.findAll();
    }

}
