package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.services.impl.MedicalRecordsServiceImpl;

@RestController
public class MedicalRecordsController {

    @Autowired MedicalRecordsServiceImpl medicalRecordsServiceImpl;
    
    @GetMapping(value="MedicalRecords")
    public List<MedicalRecordsModel> findAll() throws IOException {
	return medicalRecordsServiceImpl.findAll();
    }
}
