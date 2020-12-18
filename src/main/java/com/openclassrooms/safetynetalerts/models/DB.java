package com.openclassrooms.safetynetalerts.models;

import java.util.List;

public class DB {

    private List<PersonsModel> persons;
    private List<FireStationsModel> firestations;
    private List<MedicalRecordsModel> medicalrecords;

    public List<PersonsModel> getPersons() {
	return persons;
    }

    public void setPersons(List<PersonsModel> persons) {
	this.persons = persons;
    }

    public List<FireStationsModel> getFirestations() {
	return firestations;
    }

    public void setFirestations(List<FireStationsModel> firestations) {
	this.firestations = firestations;
    }

    public List<MedicalRecordsModel> getMedicalrecords() {
	return medicalrecords;
    }

    public void setMedicalrecords(List<MedicalRecordsModel> medicalrecords) {
	this.medicalrecords = medicalrecords;
    }

}
