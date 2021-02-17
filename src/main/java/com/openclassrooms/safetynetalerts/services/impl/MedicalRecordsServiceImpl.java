package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.repository.DBRepository;
import com.openclassrooms.safetynetalerts.services.IMedicalRecordsService;

@Repository
public class MedicalRecordsServiceImpl implements IMedicalRecordsService {

    @Autowired
    DBRepository repository;

    private static final Logger logger = LogManager.getLogger("MedicalRecordsServiceImpl");
    int medicalRecordPresentInList;

    @Override
    public List<MedicalRecordsModel> findAll() throws IOException {
	List<MedicalRecordsModel> listMedicalRecords = repository.getMedicalRecords();
	ArrayList<MedicalRecordsModel> result = new ArrayList<>();
	listMedicalRecords.forEach(medicalRegister -> {
	    if (!result.contains(medicalRegister)) {
		result.add(medicalRegister);
	    }
	});
	return result;

    }

    public boolean addNewMedicalRecords(MedicalRecordsModel newMedicalRecord) throws IOException {
	List<MedicalRecordsModel> list = repository.getMedicalRecords();
	boolean result = false;
	medicalRecordPresentInList = 0;

	if (newMedicalRecord.getFirstName() != null && newMedicalRecord.getLastName() != null
		&& newMedicalRecord.getBirthdate() != null) {

	    list.forEach(medicalRecordList -> {
		if (medicalRecordList.getFirstName().equals(newMedicalRecord.getFirstName())
			&& medicalRecordList.getLastName().equals(newMedicalRecord.getLastName())
			&& medicalRecordList.getBirthdate().equals(newMedicalRecord.getBirthdate())) {
		    medicalRecordPresentInList++;
		}
	    });

	    if (medicalRecordPresentInList == 0) {
		list.add(newMedicalRecord);
		logger.info("The medical record to " + newMedicalRecord.getFirstName() + " "
			+ newMedicalRecord.getLastName() + " was added successfully");
		result = true;
	    } else if (medicalRecordPresentInList > 0) {
		logger.error("The medical record to " + newMedicalRecord.getFirstName() + " "
			+ newMedicalRecord.getLastName() + " is already in the list");
	    } else {
		logger.error("Error encountered while adding the person");
	    }
	} else {
	    logger.error("Invalid informations, only medications and allergies can be null");
	}
	return result;

    }

    public boolean updateMedicalRecords(MedicalRecordsModel medicalRecord) throws IOException {
	List<MedicalRecordsModel> list = repository.getMedicalRecords();
	medicalRecordPresentInList = 0;
	boolean result = false;
	boolean medicalRecordsAlreadyInTheList = true;

	if (medicalRecord.getFirstName() != null && medicalRecord.getLastName() != null
		&& medicalRecord.getBirthdate() != null) {

	    if (!list.contains(medicalRecord)) {
		medicalRecordsAlreadyInTheList = false;
		list.forEach(medicalRegister -> {
		    if (medicalRegister.getFirstName().equals(medicalRecord.getFirstName())
			    && medicalRegister.getLastName().equals(medicalRecord.getLastName())) {
			medicalRegister.setBirthdate(medicalRecord.getBirthdate());
			medicalRegister.setMedications(medicalRecord.getMedications());
			medicalRegister.setAllergies(medicalRecord.getAllergies());
			medicalRecordPresentInList = 1;
		    }
		});
	    }
	    if (medicalRecordsAlreadyInTheList) {
		logger.info("The medical record corresponds with the indicated " + medicalRecord.getFirstName() + " "
			+ medicalRecord.getLastName() + " is already in the list with the same informations");
	    } else if (medicalRecordPresentInList == 1) {
		logger.info("Medical record to " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName()
			+ " update validated");
		result = true;
	    } else if (medicalRecordPresentInList == 0) {
		logger.error("No medical record corresponds with the indicated " + medicalRecord.getFirstName()
			+ " and " + medicalRecord.getLastName());
	    } else {
		logger.error("Error encountered while updating the medical record");
	    }
	} else {
	    logger.error("Invalid informations, check that you have entered all the information correctly");
	}
	return result;
    }

    public boolean deleteMedicalRecords(String firstName, String lastName) throws IOException {
	List<MedicalRecordsModel> list = repository.getMedicalRecords();
	MedicalRecordsModel medicalRecordsModel = new MedicalRecordsModel();
	medicalRecordPresentInList = 0;
	boolean result = false;

	if (firstName != null && lastName != null) {

	    list.forEach(medicalRegister -> {
		if (medicalRegister.getFirstName().equals(firstName)
			&& medicalRegister.getLastName().equals(lastName)) {
		    medicalRecordsModel.setAllergies(medicalRegister.getAllergies());
		    medicalRecordsModel.setBirthdate(medicalRegister.getBirthdate());
		    medicalRecordsModel.setFirstName(medicalRegister.getFirstName());
		    medicalRecordsModel.setLastName(medicalRegister.getLastName());
		    medicalRecordsModel.setMedications(medicalRegister.getMedications());
		    medicalRecordPresentInList = 1;
		}
	    });

	    if (list.contains(medicalRecordsModel)) {
		list.remove(medicalRecordsModel);
	    }
	    if (medicalRecordPresentInList == 0) {
		logger.error("No medical record corresponds to the name of " + firstName + " " + lastName);
	    } else if (!list.contains(medicalRecordsModel)) {
		logger.info("Medical record to " + firstName + " " + lastName + " delete with success");
		result = true;
	    } else {
		logger.error("Error encountered while deleting the person");
	    }
	} else {
	    logger.error("Invalid informations, check that you have entered firstname and lastname correctly");
	}
	return result;
    }

}
