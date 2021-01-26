package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.services.impl.FireStationsServiceImpl;

@RestController
public class FireStationsController {

    @Autowired
    FireStationsServiceImpl fireStationsServiceImpl;

    @GetMapping(value = "Firestation")
    public List<FireStationsModel> getFireStationsList() throws IOException {
	return fireStationsServiceImpl.findAll();
    }

    @GetMapping(value = "firestation")
    public MappingJacksonValue findByStationNumber(@RequestParam(value = "stationNumber") int station)
	    throws IOException, ParseException {

	HashMap<String, Object> persons = fireStationsServiceImpl.findPersonsByStationNumber(station);

	SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("email");
	FilterProvider listFilter = new SimpleFilterProvider().addFilter("URL station number", filter);

	MappingJacksonValue infosPersonFilter = new MappingJacksonValue(persons);
	infosPersonFilter.setFilters(listFilter);

	return infosPersonFilter;

    }
}
