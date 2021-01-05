package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.services.impl.FireStationsServiceImpl;

@RestController
@RequestMapping(value = "FireStations")
public class FireStationsController {

    @Autowired
    FireStationsServiceImpl fireStationsServiceImpl;

    @GetMapping(value = "")
    public List<FireStationsModel> getFireStationsList() throws IOException {
	return fireStationsServiceImpl.findAll();
    }

    @GetMapping(value = "/{station}")
    public ArrayList<PersonsModel> findByStationNumber(@PathVariable int station) throws IOException {
	return fireStationsServiceImpl.findByStationNumber(station);
    }
}
