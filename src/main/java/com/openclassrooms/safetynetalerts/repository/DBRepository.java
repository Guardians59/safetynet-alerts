package com.openclassrooms.safetynetalerts.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.CommandLineRunDB;
import com.openclassrooms.safetynetalerts.SafetynetAlertsApplication;
import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;

@Repository
public class DBRepository {

    public List<FireStationsModel> getFireStations() throws IOException {
	List<FireStationsModel> listStations = new ArrayList<>();
	listStations = CommandLineRunDB.stations;
	return listStations;
    }

    public List<PersonsModel> getPersons() throws IOException {
	List<PersonsModel> listPersons = new ArrayList<>();
	listPersons = CommandLineRunDB.persons;
	return listPersons;
    }

    public List<MedicalRecordsModel> getMedicalRecords() throws IOException {
	List<MedicalRecordsModel> listMedicalRecords = new ArrayList<>();
	listMedicalRecords = CommandLineRunDB.medical;
	return listMedicalRecords;
    }

}
