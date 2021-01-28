package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.services.impl.CommunityEmailServiceImpl;

@RestController
public class CommunityEmailController {

    @Autowired
    CommunityEmailServiceImpl communityEmailServiceImpl;

    @GetMapping(value = "communityEmail")
    public HashMap<String, Object> getEmailPersonsInTheCity(@RequestParam(value = "city") String city)
	    throws IOException {
	return communityEmailServiceImpl.findEmail(city);
    }
}
