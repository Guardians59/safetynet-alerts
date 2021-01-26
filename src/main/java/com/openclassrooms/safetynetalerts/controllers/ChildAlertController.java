package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.services.impl.ChildAlertServiceImpl;

@RestController
public class ChildAlertController {

    @Autowired
    ChildAlertServiceImpl childAlertServiceImpl;

    @GetMapping(value = "childAlert")
    public HashMap<String, Object> getListChildrenAtTheAddress(@RequestParam(value = "address") String address)
	    throws IOException, ParseException {
	return childAlertServiceImpl.findChildAtAddress(address);
    }
}
