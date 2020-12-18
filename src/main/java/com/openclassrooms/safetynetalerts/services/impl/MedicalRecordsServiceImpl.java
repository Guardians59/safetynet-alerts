package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.repository.MedicalRecordsRepository;
import com.openclassrooms.safetynetalerts.services.IMedicalRecordsService;

@Repository
public class MedicalRecordsServiceImpl implements IMedicalRecordsService {

    @Autowired MedicalRecordsRepository medicalRecordsRepository;
    
    @Override
    public List<MedicalRecordsModel> findAll() throws IOException {

	return medicalRecordsRepository.findAll();
    }

}
