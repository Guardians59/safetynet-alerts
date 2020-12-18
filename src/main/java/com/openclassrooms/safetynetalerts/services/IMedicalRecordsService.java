package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.util.List;

import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;

public interface IMedicalRecordsService {

    public List<MedicalRecordsModel> findAll() throws IOException;
    
}
