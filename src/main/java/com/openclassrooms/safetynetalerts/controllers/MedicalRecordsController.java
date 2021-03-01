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
import com.openclassrooms.safetynetalerts.services.IMedicalRecordsService;

/**
 * La classe MedicalRecordsController permet de créer l'url medicalRecord, dans
 * laquel nous pouvons récupérer la liste des antécédents médicaux enregistrés,
 * ainsi que, ajouter/modifier/supprimer un antécédent.
 * 
 * @author Dylan
 *
 */
@RestController
@RequestMapping(value = "medicalRecord")
public class MedicalRecordsController {

    @Autowired
    IMedicalRecordsService medicalRecordsService;

    /**
     * Reçoit la liste des antécédents médicaux enregistrés.
     * 
     * @return la liste des antécédents médicaux.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    @GetMapping(value = "/get")
    public List<MedicalRecordsModel> getMedicalRecordsList() throws IOException {
	return medicalRecordsService.findAll();
    }

    /**
     * Ajout d'un dossier médical.
     * 
     * @param newMedicalRecords les informations du dossier médical dans le body de
     *                          la requête.
     * @return un message validant l'ajout du dossier médical avec le status
     *         created, ou le status noContent si l'ajout n'a pas abouti.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    @PostMapping(value = "/add")
    public ResponseEntity<?> addNewMedicalRecords(@RequestBody MedicalRecordsModel newMedicalRecords)
	    throws IOException {
	var medicalAdded = medicalRecordsService.saveNewMedicalRecords(newMedicalRecords);

	if (!medicalAdded) {
	    return ResponseEntity.noContent().build();
	} else {
	    return ResponseEntity.status(HttpStatus.CREATED).body(newMedicalRecords.getFirstName() + " "
		    + newMedicalRecords.getLastName() + " medical record added with success");
	}
    }

    /**
     * Mis à jour d'un dossier médical.
     * 
     * @param medicalRecord les nouvelles informations du dossier médical dans le
     *                      body de la requête
     * @return un message validant la mis à jour du dossier médical avec le status
     *         ok, ou le status notFound si la mis à jour n'a pas aboutie.
     * @throws IOException si une erreur est rencontrée lors de la lecture données.
     */
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateMedicalRecords(@RequestBody MedicalRecordsModel medicalRecord) throws IOException {
	var medicalUpdated = medicalRecordsService.updateMedicalRecords(medicalRecord);
	if (!medicalUpdated) {
	    return ResponseEntity.notFound().build();
	} else {
	    return ResponseEntity.status(HttpStatus.OK).body(medicalRecord.getFirstName() + " "
		    + medicalRecord.getLastName() + " medical record updated with success");
	}
    }

    /**
     * Suppression d'un dossier medical.
     * 
     * @param firstName prénom de la personne.
     * @param lastName  nom de la personne.
     * @return un message validant la suppression du dossier médical avec le status
     *         ok, ou le status notFound si la suppression n'a pas aboutie.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteMedicalRecords(@RequestParam(value = "firstName") String firstName,
	    @RequestParam(value = "lastName") String lastName) throws IOException {
	var medicalDeleted = medicalRecordsService.deleteMedicalRecords(firstName, lastName);
	if (!medicalDeleted) {
	    return ResponseEntity.notFound().build();
	} else {
	    return ResponseEntity.status(HttpStatus.OK)
		    .body(firstName + " " + lastName + " medical record deleted with success");
	}
    }
}
