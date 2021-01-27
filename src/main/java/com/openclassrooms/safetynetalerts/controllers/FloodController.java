package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.services.impl.FloodServiceImpl;

@RestController
public class FloodController {

    @Autowired
    FloodServiceImpl floodServiceImpl;

    @GetMapping(value = "flood/stations")
    public HashMap<String, Object> getHomesServedByTheStation(@RequestParam(value = "stations") List<Integer> station)
	    throws IOException, ParseException {
	return floodServiceImpl.findHomesServedByTheStation(station);
    }

}
