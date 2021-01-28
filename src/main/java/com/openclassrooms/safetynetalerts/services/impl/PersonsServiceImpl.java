package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.MedicalRecordsRepository;
import com.openclassrooms.safetynetalerts.repository.PersonsRepository;
import com.openclassrooms.safetynetalerts.services.IPersonsService;

@Repository
public class PersonsServiceImpl implements IPersonsService {

    @Autowired
    PersonsRepository personsRepository;

    @Autowired
    MedicalRecordsRepository medicalRecordsRepository;

    @Override
    public List<PersonsModel> findAll() throws IOException {

	return personsRepository.findAll();

    }

}
