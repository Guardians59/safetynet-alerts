package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.services.impl.FireServiceImpl;

@RestController
public class FireController {

    @Autowired
    FireServiceImpl fireServiceImpl;
    
    @GetMapping(value= "fire")
    public HashMap<String, Object> getPersonsAndFireStationAtTheAddress(@RequestParam(value = "address") String address)
	    throws IOException, ParseException {
	return fireServiceImpl.findPersonsAndFireStationAtTheAddress(address);
    }
}
