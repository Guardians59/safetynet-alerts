package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.services.impl.PersonsServiceImpl;

@RestController
public class CommunityEmailController {

    @Autowired
    PersonsServiceImpl personsServiceImpl;

    @GetMapping(value = "communityEmail")
    public ArrayList<String> getEmailPersonsInTheCity(@RequestParam(value = "city") String city) throws IOException {
	return personsServiceImpl.findEmail(city);
    }
}
