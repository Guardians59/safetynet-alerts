package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.repository.DBRepository;
import com.openclassrooms.safetynetalerts.services.IMedicalRecordsService;

/**
 * La classe MedicalRecordsServiceImpl est l'implémentation de l'interface
 * IMedicalRecordsService.
 * 
 * @see IMedicalRecordsService
 * @author Dylan
 */
@Service
public class MedicalRecordsServiceImpl implements IMedicalRecordsService {

    @Autowired
    DBRepository repository;

    private static Logger logger = LogManager.getLogger(MedicalRecordsServiceImpl.class);
    int medicalRecordPresentInList;

    @Override
    public List<MedicalRecordsModel> findAll() throws IOException {
	List<MedicalRecordsModel> listMedicalRecords = repository.getMedicalRecords();
	ArrayList<MedicalRecordsModel> result = new ArrayList<>();
	logger.debug("Search the list of registered medical records");
	listMedicalRecords.forEach(medicalRegister -> {
	    if (!result.contains(medicalRegister)) {
		result.add(medicalRegister);
	    }
	});
	return result;

    }

    @Override
    public boolean saveNewMedicalRecords(MedicalRecordsModel newMedicalRecord) throws IOException {
	List<MedicalRecordsModel> list = repository.getMedicalRecords();
	boolean result = false;
	medicalRecordPresentInList = 0;

	/**
	 * Si les infos du dossier médical sont valides nous vérifions si il n'est pas
	 * déjà présent dans la liste, auquel cas nous l'ajoutons et indiquons true au
	 * boolean qui sera renvoyer, sinon nous laissons le boolean sur false pour
	 * indiquer que l'ajout n'est pas validé.
	 */
	if (newMedicalRecord.getFirstName() != null && newMedicalRecord.getLastName() != null
		&& newMedicalRecord.getBirthdate() != null) {
	    logger.debug("Adding the new medical record");

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

    @Override
    public boolean updateMedicalRecords(MedicalRecordsModel medicalRecord) throws IOException {
	List<MedicalRecordsModel> list = repository.getMedicalRecords();
	medicalRecordPresentInList = 0;
	boolean result = false;
	boolean medicalRecordsAlreadyInTheList = true;

	/**
	 * Nous vérifions que le prénom, nom et la date de naissance soient valides, si
	 * c'est le cas nous vérifions si ce dossier a bien été modifié et ne soit pas
	 * un doublon, si ce n'est pas un doublon nous utilisons une boucle forEach dans
	 * la liste afin de retrouver le dossier de la personne et de modifier les
	 * informations, nous indiquons true au boolean afin de valider la modification,
	 * auquel cas il reste sur false pour indiqué que la mis à jour ne s'est pas
	 * executée.
	 */
	if (medicalRecord.getFirstName() != null && medicalRecord.getLastName() != null
		&& medicalRecord.getBirthdate() != null) {
	    logger.debug("Updating the medical records of " + medicalRecord.getFirstName() + " "
		    + medicalRecord.getLastName());

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

    @Override
    public boolean deleteMedicalRecords(String firstName, String lastName) throws IOException {
	List<MedicalRecordsModel> list = repository.getMedicalRecords();
	MedicalRecordsModel medicalRecordsModel = new MedicalRecordsModel();
	medicalRecordPresentInList = 0;
	boolean result = false;

	/**
	 * Nous vérifions que le prénom et nom soient valides, ensuite nous utilisons
	 * une boucle forEach pour retrouver dans la liste des dossiers médicaux celui
	 * correspondant aux noms entrées en paramètre, nous supprimons le dossier et
	 * vérifions ensuite qu'il n'est plus présent dans la liste afin de passer le
	 * boolean en true pour valider la suppression, auquel cas nous le laissons sur
	 * false pour indiquer que la suppression ne s'est pas executé.
	 */
	if (firstName != null && lastName != null) {
	    logger.debug("Deleting the medical record of " + firstName + " " + lastName);

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
