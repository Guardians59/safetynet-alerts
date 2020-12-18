package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.services.impl.FireStationsServiceImpl;

@RestController
public class FireStationsController {

    @Autowired FireStationsServiceImpl fireStationsServiceImpl;
    
    @GetMapping(value="FireStations")
    public List<FireStationsModel> findAll() throws IOException {
	return fireStationsServiceImpl.findAll();
    }
}
