package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.services.impl.MedicalRecordsServiceImpl;

@RestController
@RequestMapping(value = "medicalRecord")
public class MedicalRecordsController {

    @Autowired
    MedicalRecordsServiceImpl medicalRecordsServiceImpl;

    @GetMapping(value = "/get")
    public List<MedicalRecordsModel> getMedicalRecordsList() throws IOException {
	return medicalRecordsServiceImpl.findAll();
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addNewMedicalRecords(@RequestBody MedicalRecordsModel newMedicalRecords)
	    throws IOException {
	var medicalAdded = medicalRecordsServiceImpl.addNewMedicalRecords(newMedicalRecords);

	if (!medicalAdded) {
	    return ResponseEntity.noContent().build();
	} else {
	    return ResponseEntity.status(HttpStatus.CREATED).body(newMedicalRecords.getFirstName() + " "
		    + newMedicalRecords.getLastName() + " medical record added with success");
	}
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateMedicalRecords(@RequestBody MedicalRecordsModel medicalRecord) throws IOException {
	var medicalUpdated = medicalRecordsServiceImpl.updateMedicalRecords(medicalRecord);
	if (!medicalUpdated) {
	    return ResponseEntity.notFound().build();
	} else {
	    return ResponseEntity.status(HttpStatus.OK).body(medicalRecord.getFirstName() + " "
		    + medicalRecord.getLastName() + " medical record updated with success");
	}
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteMedicalRecords(@RequestParam(value = "firstName") String firstName,
	    @RequestParam(value = "lastName") String lastName) throws IOException {
	var medicalDeleted = medicalRecordsServiceImpl.deleteMedicalRecords(firstName, lastName);
	if (!medicalDeleted) {
	    return ResponseEntity.notFound().build();
	} else {
	    return ResponseEntity.status(HttpStatus.OK).body(firstName + " "
		    + lastName + " medical record deleted with success");
	}
    }
}
